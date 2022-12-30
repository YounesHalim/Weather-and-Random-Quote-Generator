package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.WeatherReportApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainController implements Initializable {
    @FXML
    private BorderPane mainApplicationLayout;
    @FXML
    private Button weather, quotes;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        weather.setCursor(Cursor.HAND);
        quotes.setCursor(Cursor.HAND);
        weather.setOnAction(actionEvent ->
                new Thread(()-> setInterface(interfaceLoader.WEATHER_INTERFACE.getInterface())).start());
        quotes.setOnAction(actionEvent ->
                new Thread(()-> setInterface(interfaceLoader.QUOTE_INTERFACE.getInterface())).start());
    }

    public enum interfaceLoader {
        SEARCH_BAR_INTERFACE("fxml/seachScene.fxml"),
        WEATHER_INTERFACE("fxml/weatherScene.fxml"),
        NEWS_INTERFACE("newsScene.fxml"),
        MENU_NAV("fxml/menuNavigator.fxml"),
        QUOTE_INTERFACE("fxml/QuotesEditor.fxml"),
        MAIN("MainScene.fxml");
        private String fxmlFile;

        interfaceLoader(String fxmlFile) {
            this.fxmlFile = fxmlFile;
        }

        public String getInterface() {
            return fxmlFile;
        }
    }

    private void setInterface(String scene) {
        Platform.runLater(()-> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(WeatherReportApplication.class.getResource(scene));
                AnchorPane anchorPane = loader.load();
                mainApplicationLayout.setCenter(anchorPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
