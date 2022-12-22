package com.weatherreport.weatherreport.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WeatherReportController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}