package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.WeatherReportApplication;
import com.weatherreport.weatherreport.model.location.GeographicLocation;
import com.weatherreport.weatherreport.model.meteorology.MainWeather;
import com.weatherreport.weatherreport.model.meteorology.Meteorology;
import com.weatherreport.weatherreport.model.meteorology.Sys;
import com.weatherreport.weatherreport.model.meteorology.Weather;
import com.weatherreport.weatherreport.service.ApiWeatherCallService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherReportController implements Initializable {
    @FXML public AnchorPane rightAnchorPane;
    @FXML private Label newsTitle, feelsLike, sunsetHour,sunriseHour, degree, countryLocation, weatherDescription, mainDescription;
    @FXML private Label pressure, humidity, visibility;
    @FXML private Circle weatherIcon, sunsetIcon, sunriseIcon;
    public static GeographicLocation defaultLocation = GeographicLocation.builder().country("CA").name("Montreal").build();
    private String celsius = "%d°C";
    private void setWeatherIcon(Meteorology forecast) {
        String icon = new Weather().getWeatherAttributes(forecast, Weather::getIcon);
        try {
            String iconPath = ApiWeatherCallService.getApiWeatherCallServiceInstance().getAppropriateWeatherIcon(icon).toString();
            weatherIcon.setFill(new ImagePattern(new Image(Objects.requireNonNull(iconPath))));
            sunsetIcon.setFill(new ImagePattern(new Image(Objects.requireNonNull(WeatherReportApplication.class.getResourceAsStream("icons/sunset.png")))));
            sunriseIcon.setFill(new ImagePattern(new Image(Objects.requireNonNull(WeatherReportApplication.class.getResourceAsStream("icons/sunrise.png")))));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setHeliosTime(String firstOrLastLight, long sunriseSunsetTime) {
        Date convertedDate = new Date(sunriseSunsetTime * 1000);
        switch (firstOrLastLight) {
            case "LAST" ->
                    sunsetHour.setText(sunsetHour.getText() + " " + convertedDate.getHours() + ":" + convertedDate.getMinutes());
            case "FIRST"->
                    sunriseHour.setText(sunriseHour.getText() + " " + convertedDate.getHours() + ":" + convertedDate.getMinutes());
        }
    }
    private void setCountryLocation(Meteorology forecast, Sys country) {
        String s = "%s, %s".formatted(forecast.getName(), country.getCountry());
        countryLocation.setText(s);
        visibility.setText(MessageFormat.format("{0} km", forecast.getVisibility()));
    }
    private void setFeelsLike(MainWeather mainWeather) {
        int feelsLikeTemp = (int) (Math.floor(mainWeather.getFeels_like()));
        feelsLike.setText(String.format(celsius,feelsLikeTemp));
    }
    private void setTemperature(MainWeather mainWeather) {
        degree.setText(String.format(celsius,(int) Math.floor(mainWeather.getTemp())));
        pressure.setText((MessageFormat.format("{0} hPa", mainWeather.getPressure())));
        humidity.setText(MessageFormat.format("{0}%",mainWeather.getHumidity()));
    }
    private void setDescription(String description) {
        // Capitalize
        mainDescription.setText(Arrays.stream(description.split(" "))
                .map((word)-> word.toUpperCase().charAt(0) + word.substring(1))
                .collect(Collectors.joining("\n")));
    }
    private void setMainDescription(String description)  {
        weatherDescription.setText(description);
    }
    protected Meteorology setWeatherApiCall(GeographicLocation news) {
        return ApiWeatherCallService.getApiWeatherCallServiceInstance().weatherApiCall(news);
    }
    public static void setDefaultLocation(GeographicLocation defaultLocation) {
        WeatherReportController.defaultLocation = defaultLocation;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Meteorology forecast = setWeatherApiCall(defaultLocation);
        setWeatherIcon(forecast);
        long sunset = forecast.getSys().getSunset();
        long sunrise = forecast.getSys().getSunrise();
        setHeliosTime("LAST", sunset);
        setHeliosTime("FIRST", sunrise);
        setCountryLocation(forecast, forecast.getSys());
        setFeelsLike(forecast.getMain());
        setTemperature(forecast.getMain());
        setDescription(new Weather().getWeatherAttributes(forecast, Weather::getDescription));
        setMainDescription(new Weather().getWeatherAttributes(forecast, Weather::getMain));
    }
}