package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.WeatherReportApplication;
import com.weatherreport.weatherreport.model.location.GeographicLocation;
import com.weatherreport.weatherreport.model.meteorology.MainWeather;
import com.weatherreport.weatherreport.model.meteorology.Meteorology;
import com.weatherreport.weatherreport.model.meteorology.Sys;
import com.weatherreport.weatherreport.model.meteorology.Weather;
import com.weatherreport.weatherreport.service.ApiWeatherCallService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class WeatherReportController implements Initializable {
    @FXML private Label date, feelsLike, sunsetHour, degree, countryLocation;
    @FXML private Circle weatherIcon;
    @FXML private BorderPane mainApplicationLayout;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Meteorology forecast = getUserLocationBasedOnInput();
        setWeatherIcon(forecast);
        long sunset = forecast.getSys().getSunset();
        setHeliosTime("LAST",sunset);
        setCountryLocation(forecast, forecast.getSys());
        setFeelsLike(forecast.getMain());
        setTemperature(forecast.getMain());
        setDate();
        sceneInjector("SearchBar-Scene");
    }

    private void setWeatherIcon(Meteorology forecast) {
        String icon = new Weather().getWeatherAttributes(forecast, Weather::getIcon);
        try {
            String iconPath = ApiWeatherCallService.getApiWeatherCallServiceInstance().getAppropriateWeatherIcon(icon).toString();
            weatherIcon.setFill(new ImagePattern(new Image(Objects.requireNonNull(iconPath))));
        }catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
    private void setHeliosTime(String firstOrLastLight, long sunriseSunsetTime) {
        Date convertedDate = new Date(sunriseSunsetTime * 1000);
        switch (firstOrLastLight) {
            case "LAST" -> sunsetHour.setText(sunsetHour.getText()+" "+convertedDate.getHours()+":"+convertedDate.getMinutes());
        }
    }
    private void setCountryLocation(Meteorology forecast, Sys country) {
        countryLocation.setText("%s, %s".formatted(forecast.getName(), country.getCountry()));
    }
    private void setFeelsLike(MainWeather mainWeather) {
        int feelsLikeTemp = (int) (Math.floor(mainWeather.getFeels_like()));
        feelsLike.setText("%s %d".formatted(feelsLike.getText(), feelsLikeTemp));
    }
    private void setTemperature(MainWeather mainWeather) {
        degree.setText(String.valueOf(Math.floor(mainWeather.getTemp())));
    }
    private void setDate() {
        date.setText(new Date().toString());
    }
    private Meteorology getUserLocationBasedOnInput() {
        // Work in progress
        GeographicLocation defaultGeoLocation = GeographicLocation
                .builder()
                .name("Montreal")
                .country("Canada")
                .measureUnits("metric")
                .build();

        Meteorology forecast = ApiWeatherCallService
                .getApiWeatherCallServiceInstance()
                .getMeteorologyObject(defaultGeoLocation);
        return forecast;
    }
    private void sceneInjector(String FXMLFileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(WeatherReportApplication.class.getResource(FXMLFileName+".fxml"));
            AnchorPane searchBar = fxmlLoader.load();
            mainApplicationLayout.setTop(searchBar);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}