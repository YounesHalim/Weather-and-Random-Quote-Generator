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


public class SearchBarController implements Initializable{
    @FXML
    private TextField searchBar;
    @FXML private AnchorPane anchorPane;
    public static List<String> worldCitiesList = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        autoCompletionBinding();
        searchBar.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER ->  {
                    if(!searchBar.getText().isEmpty() || !searchBar.getText().isBlank()) {
                        Function<TextField, String[]> nextDestination = (textField) -> textField.getText().split(",");
                        new Thread(()-> {
                            GeographicLocation newLocation = searchedLocation(nextDestination);
                            WeatherReportController.setDefaultLocation(newLocation);
                            Platform.runLater(()-> {
                                try {
                                    newDestinationSetter(keyEvent, newLocation);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }).start();

                    }
                }
            }
        });
    }

    private static void newDestinationSetter(KeyEvent keyEvent, GeographicLocation newLocation) throws IOException {
        FXMLLoader loader = new FXMLLoader(WeatherReportApplication.class.getResource("Weather-Main-Scene.fxml"));
        Parent parent = loader.load();
        WeatherReportController reportController = loader.getController();
        reportController.setWeatherApiCall(newLocation);
        Stage stage = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
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
