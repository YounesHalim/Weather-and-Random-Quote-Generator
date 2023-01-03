package com.weatherreport.weatherreport;


import com.weatherreport.weatherreport.controllers.SearchBarController;
import com.weatherreport.weatherreport.service.UnsplashService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class WeatherReportApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WeatherReportApplication.class.getResource("MainScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Weather report");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        new Thread(SearchBarController::fetchJSONData).start();
        new Thread(()-> new UnsplashService().setListOfURLs()).start();
        launch();
    }
}