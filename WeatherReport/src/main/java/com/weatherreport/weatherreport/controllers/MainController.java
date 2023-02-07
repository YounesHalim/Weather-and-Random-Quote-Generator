package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.WeatherReportApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainController implements Initializable {
    @FXML private BorderPane mainApplicationLayout;
    @FXML private VBox vboxOptionMenu;
    @FXML private Circle weather, quotes;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setIcons();
        cursorHandler();
        weather.setOnMouseClicked(actionEvent -> setInterface(interfaceLoader.WEATHER_INTERFACE.getInterface()));
        quotes.setOnMouseClicked(actionEvent -> setInterface(interfaceLoader.QUOTE_INTERFACE.getInterface()));
    }
    public enum interfaceLoader {
        MENU_NAV("fxml/menuNavigator.fxml"),
        QUOTE_INTERFACE("fxml/QuotesEditor.fxml"),
        SHARE_INTERFACE("fxml/share.fxml"),
        WEATHER_INTERFACE("fxml/weatherScene.fxml");
        private final String fxmlFile;

        interfaceLoader(String fxmlFile) {
            this.fxmlFile = fxmlFile;
        }
        public String getInterface() {
            return fxmlFile;
        }
    }
    private void setInterface(String scene) {
        Platform.runLater(() -> {
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
    private void cursorHandler() {
        vboxOptionMenu.getChildren().forEach(node -> node.setCursor(Cursor.HAND));
    }

    private void setIcons() {
        weather.setFill(new ImagePattern(new Image(Objects.requireNonNull(WeatherReportApplication.class.getResourceAsStream("icons/weather.png")))));
        quotes.setFill(new ImagePattern(new Image(Objects.requireNonNull(WeatherReportApplication.class.getResourceAsStream("icons/portrait2.jpeg")))));
    }
}
