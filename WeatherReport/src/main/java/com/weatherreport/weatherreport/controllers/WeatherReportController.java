package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.model.location.GeographicLocation;
import com.weatherreport.weatherreport.model.meteorology.MainWeather;
import com.weatherreport.weatherreport.model.meteorology.Meteorology;
import com.weatherreport.weatherreport.model.meteorology.Sys;
import com.weatherreport.weatherreport.model.meteorology.Weather;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.weatherreport.weatherreport.service.WeatherService.getWeatherServiceInstance;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherReportController {
    @FXML
    private AnchorPane weatherAnchorPane;
    @FXML
    private TextField searchBar;
    @FXML
    private Label lowTemp, highTemp, newsTitle, feelsLike, sunsetHour, sunriseHour, degree, countryLocation, weatherDescription, mainDescription;
    @FXML
    private Label pressure, humidity, visibility, windData;
    @FXML private Rectangle bgImage;
    @FXML private Circle weatherIcon, sunsetIcon, sunriseIcon, visibilityIcon, humidityIcon, pressureIcon, windIcon, minTempIcon, maxTempIcon;
    public static GeographicLocation defaultLocation = GeographicLocation.builder().country("CA").name("Montreal").lng("45.5019").lat("73.5674").build();
    private String celsius = "%d°C";

    public void initialize() {
        new SearchBarController().searchBarExecutioner(searchBar);
        searchBarFunction();
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

    private void setWeatherIcon(Meteorology forecast) {
        String icon = new Weather().getWeatherAttributes(forecast, Weather::getIcon);
        try {
            String iconPath = getWeatherServiceInstance().getAppropriateWeatherIcon(icon).toString();
            CompletableFuture<Image> imageCompletableFuture = CompletableFuture.supplyAsync(()-> new Image(Objects.requireNonNull(iconPath), 32, 32, false, false));
            weatherIcon.setFill(new ImagePattern(imageCompletableFuture.join()));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setHeliosTime(String firstOrLastLight, long sunriseSunsetTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sunriseSunsetTime * 1000);
        switch (firstOrLastLight) {
            case "LAST" -> sunsetHour.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
            case "FIRST" -> sunriseHour.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        }
    }

    private void setCountryLocation(Meteorology forecast, Sys country) {
        String s = "%s, %s".formatted(forecast.getName(), country.getCountry());
        countryLocation.setText(s);
        visibility.setText(MessageFormat.format("{0}km", forecast.getVisibility() / 1000));
        windData.setText(MessageFormat.format("{0}km/h", (int) Math.floor(forecast.getWind().getSpeed())));
    }

    private void setFeelsLike(MainWeather mainWeather) {
        int feelsLikeTemp = (int) (Math.floor(mainWeather.getFeels_like()));
        feelsLike.setText(String.format(celsius, feelsLikeTemp));
    }

    private void setTemperature(MainWeather mainWeather) {
        degree.setText(String.format(celsius, (int) Math.floor(mainWeather.getTemp())));
        pressure.setText((MessageFormat.format("{0}hPa", mainWeather.getPressure())));
        humidity.setText(MessageFormat.format("{0}%", mainWeather.getHumidity()));
        lowTemp.setText(String.format(celsius, (int) Math.floor(mainWeather.getTemp_min())));
        highTemp.setText(String.format(celsius, (int) Math.floor(mainWeather.getTemp_max())));

    }

    private void setDescription(String description) {
        // Capitalize
        mainDescription.setText(Arrays.stream(description.split(" "))
                .map((word) -> word.toUpperCase().charAt(0) + word.substring(1))
                .collect(Collectors.joining(" ")));
    }

    private void setMainDescription(String description) {
        weatherDescription.setText(description);
    }

    private Meteorology setWeatherApiCall(GeographicLocation news) {
        return getWeatherServiceInstance().weatherApiCall(news);
    }

    public static void setDefaultLocation(GeographicLocation defaultLocation) {
        WeatherReportController.defaultLocation = defaultLocation;
    }

    private void searchBarFunction() {
        searchBar.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER -> {
                    if (!searchBar.getText().isEmpty() || !searchBar.getText().isBlank()) {
                        Function<TextField, String[]> nextDestination = (textField) -> textField.getText().split(",");
                        GeographicLocation newLocation = new SearchBarController().searchedLocation(searchBar, nextDestination);
                        WeatherReportController.setDefaultLocation(newLocation);
                        initialize();
                    }
                }
                case BACK_SPACE -> searchBar.setText("");
            }
        });
    }
}