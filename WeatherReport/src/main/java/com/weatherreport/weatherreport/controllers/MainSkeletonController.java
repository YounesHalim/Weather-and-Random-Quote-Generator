package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.WeatherReportApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainSkeletonController{
    @FXML private BorderPane mainApplicationLayout;
    public void initialize() {
        try {
            weatherInterfaceLoader();
            searchBarLoader();
            newsInterfaceLoader();
            widgetInterfaceLoader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void weatherInterfaceLoader() throws IOException {
        FXMLLoader rightPaneLoader = new FXMLLoader();
        rightPaneLoader.setLocation(WeatherReportApplication.class.getResource("todaysOverViewLayout.fxml"));
        AnchorPane weatherLookUp = rightPaneLoader.load();
        mainApplicationLayout.setCenter(weatherLookUp);
    }

    private void searchBarLoader() throws IOException {
        FXMLLoader topPaneLoader = new FXMLLoader();
        topPaneLoader.setLocation(WeatherReportApplication.class.getResource("SearchBar-Scene.fxml"));
        AnchorPane searchBar = topPaneLoader.load();
        mainApplicationLayout.setTop(searchBar);
    }

    private void newsInterfaceLoader() throws IOException {
        FXMLLoader rightPaneLoader = new FXMLLoader();
        rightPaneLoader.setLocation(WeatherReportApplication.class.getResource("newsScene.fxml"));
        AnchorPane newsInterface = rightPaneLoader.load();
        mainApplicationLayout.setRight(newsInterface);
    }
    private void widgetInterfaceLoader() throws IOException {
        FXMLLoader leftPaneLoader = new FXMLLoader();
        leftPaneLoader.setLocation(WeatherReportApplication.class.getResource("citiesWidget.fxml"));
        AnchorPane widgetInterface = leftPaneLoader.load();
        mainApplicationLayout.setLeft(widgetInterface);
    }
}
