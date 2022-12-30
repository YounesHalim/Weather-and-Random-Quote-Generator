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
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Menu;
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

public class SearchBarController implements Initializable {
    @FXML
    private TextField searchBar;
    @FXML
    private MenuItem closeApplicationButton;
    @FXML
    private Menu helpButton, optionsButton;
    public static List<String> worldCitiesList = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helpButton.setStyle("-fx-text-fill: white");
        optionsButton.setStyle("-fx-text-fill: white");
        autoCompletionBinding();
        searchBar.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER -> {
                    if (!searchBar.getText().isEmpty() || !searchBar.getText().isBlank()) {
                        Function<TextField, String[]> nextDestination = (textField) -> textField.getText().split(",");
                        GeographicLocation newLocation = searchedLocation(nextDestination);
                        WeatherReportController.setDefaultLocation(newLocation);
                        Platform.runLater(()-> sceneRefresh(keyEvent));
                    }
                }
            }
        });

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
}
