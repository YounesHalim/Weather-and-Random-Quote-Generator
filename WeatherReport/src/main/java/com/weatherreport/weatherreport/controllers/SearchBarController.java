package com.weatherreport.weatherreport.controllers;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.WeatherReportApplication;
import com.weatherreport.weatherreport.model.location.GeographicLocation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

import static com.weatherreport.weatherreport.service.ApiGNewsService.*;


public class SearchBarController implements Initializable {
    @FXML
    private TextField searchBar;
    @FXML
    private MenuItem closeApplicationButton;
    public static List<String> worldCitiesList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        autoCompletionBinding();
        searchBar.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER -> {
                    if (!searchBar.getText().isEmpty() || !searchBar.getText().isBlank()) {
                        Function<TextField, String[]> nextDestination = (textField) -> textField.getText().split(",");
                        GeographicLocation newLocation = searchedLocation(nextDestination);
                        WeatherReportController.setDefaultLocation(newLocation);
                        Platform.runLater(() -> newDestination(newLocation));
                        Platform.runLater(()-> sceneRefresh(keyEvent));
                    }
                }
            }
        });
        quitAndFlush();
    }

    private static void sceneRefresh(KeyEvent keyEvent) {
        FXMLLoader sceneLoader = new FXMLLoader(WeatherReportApplication.class.getResource("MainScene.fxml"));
        Parent root;
        try {
            root = sceneLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 460);
        stage.setScene(scene);
        stage.show();
    }

    private static void newDestination(GeographicLocation newLocation) {
        try {
            FXMLLoader loader = new FXMLLoader(WeatherReportApplication.class.getResource("todaysOverViewLayout.fxml"));
            loader.load();
            WeatherReportController reportController = loader.getController();
            new Thread(() -> reportController.setWeatherApiCall(newLocation)).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void fetchJSONData() {
        String path = "src/main/resources/com/weatherreport/weatherreport/cities.json";
        Gson gson = new Gson();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            GeographicLocation data = gson.fromJson(bufferedReader, GeographicLocation.class);
            worldCitiesList = data.getData()
                    .parallelStream()
                    .map((city) -> city.getName() + ", " + city.getCountry())
                    .toList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void autoCompletionBinding() {
        TextFields.bindAutoCompletion(searchBar, worldCitiesList);
    }

    protected GeographicLocation searchedLocation(Function<TextField, String[]> searchedLocation) {
        return GeographicLocation
                .builder()
                .name(searchedLocation.apply(searchBar)[0].trim())
                .country(searchedLocation.apply(searchBar)[1].trim())
                .build();
    }
    protected void quitAndFlush() {
        closeApplicationButton.setOnAction((actionEvent)-> {
            getGNewsInstance().fileDeleter();
            System.exit(0);
        });

    }
}
