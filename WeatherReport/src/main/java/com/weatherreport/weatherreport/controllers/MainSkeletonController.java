package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.WeatherReportApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainSkeletonController {
    @FXML
    private BorderPane mainApplicationLayout;
    public enum interfaceLoader {
        SEARCH_BAR("SearchBar-Scene.fxml"),
        WEATHER_INTERFACE("todaysOverviewLayout.fxml"),
        NEWS_INTERFACE("newsScene.fxml"),
        MENU_NAV("menuNavigator.fxml"),
        MAIN("MainScene.fxml");
        private String fxmlFile;
        interfaceLoader(String fxmlFile) {
            this.fxmlFile = fxmlFile;
        }
        public String getInterface() {
            return fxmlFile;
        }
    }
    public void initialize() {
        weatherInterfaceLoader();
        searchBarLoader();
        menuNavigation();
    }

    private void weatherInterfaceLoader() {
        FXMLLoader rightPaneLoader = new FXMLLoader();
        rightPaneLoader.setLocation(WeatherReportApplication.class.getResource(interfaceLoader.WEATHER_INTERFACE.getInterface()));
        StackPane weatherLookUp;
        try {
            weatherLookUp = rightPaneLoader.load();
            weatherLookUp.setCache(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainApplicationLayout.setCenter(weatherLookUp);
    }

    private void searchBarLoader() {

        FXMLLoader topPaneLoader = new FXMLLoader();
        topPaneLoader.setLocation(WeatherReportApplication.class.getResource(interfaceLoader.SEARCH_BAR.getInterface()));
        AnchorPane searchBar;
        try {
            searchBar = topPaneLoader.load();
            searchBar.setCache(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainApplicationLayout.setTop(searchBar);
    }

    private void newsInterfaceLoader(){
        FXMLLoader rightPaneLoader = new FXMLLoader();
        rightPaneLoader.setLocation(WeatherReportApplication.class.getResource(interfaceLoader.NEWS_INTERFACE.getInterface()));
        Pane newsInterface;
        try {
            newsInterface = rightPaneLoader.load();
            newsInterface.setCache(true);
        }catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        mainApplicationLayout.setCenter(newsInterface);
    }

    private void menuNavigation() {
        FXMLLoader leftPaneLoader = new FXMLLoader();
        leftPaneLoader.setLocation(WeatherReportApplication.class.getResource(interfaceLoader.MENU_NAV.getInterface()));
        AnchorPane widgetInterface;
        try {
            widgetInterface = leftPaneLoader.load();
            widgetInterface.setCache(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainApplicationLayout.setLeft(widgetInterface);
    }
}
