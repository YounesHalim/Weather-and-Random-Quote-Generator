package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.WeatherReportApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.weatherreport.weatherreport.controllers.MainController.*;

public class MenuNavigation implements Initializable {
    @FXML
    private AnchorPane menuController;

    @FXML
    private Button weather, news;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        weather.setCursor(Cursor.HAND);
        news.setCursor(Cursor.HAND);
        weather.setOnAction(actionEvent -> Platform.runLater(() -> {
            weatherInterfaceLoader(actionEvent);
        }));

        news.setOnAction(actionEvent -> Platform.runLater(() -> {
            newsInterfaceLoader(actionEvent);

        }));

    }

    private static void newsInterfaceLoader(ActionEvent actionEvent) {
        FXMLLoader rootLoader = new FXMLLoader(WeatherReportApplication.class.getResource(interfaceLoader.MAIN.getInterface()));
        FXMLLoader newsLoader = new FXMLLoader(WeatherReportApplication.class.getResource(interfaceLoader.NEWS_INTERFACE.getInterface()));

        try {
            Parent root = rootLoader.load();
            newsLoader.load();
            MainController mainController = rootLoader.getController();
            NewsReportsController newsReportsController = newsLoader.getController();
            Pane newsPane = newsReportsController.newsPane;
            mainController.getMainApplicationLayout().setCenter(newsPane);
            sceneRefresher(actionEvent, root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void weatherInterfaceLoader(ActionEvent actionEvent) {
        FXMLLoader rootLoader = new FXMLLoader(WeatherReportApplication.class.getResource(interfaceLoader.MAIN.getInterface()));
        FXMLLoader weatherLoader = new FXMLLoader(WeatherReportApplication.class.getResource(interfaceLoader.WEATHER_INTERFACE.getInterface()));
        try {
            Parent root = rootLoader.load();
            weatherLoader.load();
            MainController mainController = rootLoader.getController();
            WeatherReportController reportController = weatherLoader.getController();
            StackPane weatherStackPane = reportController.weatherStackPane;
            mainController.getMainApplicationLayout().setCenter(weatherStackPane);
            sceneRefresher(actionEvent, root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sceneRefresher(ActionEvent actionEvent, Parent root) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 460);
        stage.setScene(scene);
        stage.show();
    }

}
