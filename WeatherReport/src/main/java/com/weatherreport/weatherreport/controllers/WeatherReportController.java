package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.model.location.GeographicLocation;
import com.weatherreport.weatherreport.model.meteorology.Meteorology;
import com.weatherreport.weatherreport.model.meteorology.Weather;
import com.weatherreport.weatherreport.service.ApiWeatherCallService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class WeatherReportController implements Initializable {
    @FXML
    private Label date, feelsLike, sunsetHour, degree, countryLocation;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GeographicLocation defaultGeoLocation = GeographicLocation
                .builder()
                .city("Montreal")
                .country("Canada")
                .measureUnits("metric")
                .build();
        Meteorology forecast = ApiWeatherCallService
                .getApiWeatherCallServiceInstance()
                .getMeteorologyObject(defaultGeoLocation);

        String cityName = forecast.getName();
        String countryName = forecast.getSys().getCountry().toUpperCase();
        int feels_like = (int) (Math.floor(forecast.getMain().getFeels_like()));
        int temp = (int) Math.floor(forecast.getMain().getTemp());

        feelsLike.setText("%s %d".formatted(feelsLike.getText(), feels_like));
        countryLocation.setText("%s, %s".formatted(cityName, countryName));
        degree.setText(String.valueOf(temp));

    }
}