package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.model.news.Articles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.weatherreport.weatherreport.service.ApiGNewsService.Type;
import static com.weatherreport.weatherreport.service.ApiGNewsService.getGNewsInstance;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainController implements Initializable {
    @FXML
    private BorderPane mainApplicationLayout;
    List<Articles> listOfArticles = NewsReportsController.getNewsObject().getArticles();
    List<String> listOfImages = getGNewsInstance().getListOfHeadlinesURL(listOfArticles, Type.IMAGES);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {
            try {
                getGNewsInstance().thumbnailsDownloader(listOfImages);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

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
}
