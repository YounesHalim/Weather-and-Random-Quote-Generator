package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.model.news.Articles;
import com.weatherreport.weatherreport.model.news.News;
import com.weatherreport.weatherreport.model.news.NewsObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.weatherreport.weatherreport.service.ApiGNewsService.*;

public class NewsReportsController implements Initializable {
    @FXML
    private Rectangle mainImageArticle;
    @FXML private Label textDescription, pageIndex, sourceLink;
    @FXML private Button nextArticle, previousArticle;
    @FXML public Pane newsPane;
    private static int indexCount = 0;
    public static News defaultNews = News.builder().language("EN").topic("breaking-news").country("CA").build();
    public static NewsObject newsObject = getGNewsInstance().deserializeGNewsJsonObject(defaultNews);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Articles> articlesList = newsObject.getArticles();
        int size = articlesList.size();
        setArticleImage(articlesList, indexCount);
        setArticleDescription(articlesList, indexCount);
        setSourceLink(articlesList,indexCount);
        nextArticle.setOnAction(actionEvent -> {
            showNext(articlesList, size);
            pageIndex.setText(String.valueOf(indexCount));
        });

        previousArticle.setOnAction(actionEvent -> {
            showPrevious(articlesList, size);
            pageIndex.setText(String.valueOf(indexCount));
        });

    }
    private void setArticleImage(List<Articles> listOfArticles, int indexPOS) {
        List<String> listOfImages = getGNewsInstance().getListOfHeadlinesURL(listOfArticles, Type.IMAGES);
        int size = listOfImages.size();
        if (size == 0) {
            return;
        }
        String url = listOfImages.get(indexPOS);
        String[] extensions = {".jpeg",".jpg",".png",".webmp",".gif"};
        for(String str: extensions) {
            if(url.endsWith(str)) {
                mainImageArticle.setFill(new ImagePattern(new Image(Objects.requireNonNull(url))));
                break;
            } else if (url.contains(str)) {
                String constructedUrl = url.split(str)[0] + str;
                mainImageArticle.setFill(new ImagePattern(new Image(Objects.requireNonNull(constructedUrl))));
                break;
            }
        }
        mainImageArticle.setFill(new ImagePattern(new Image(Objects.requireNonNull(url))));
    }

    private void setSourceLink(List<Articles> articlesList, int indexPOS) {
        List<String> listOfSourceLinks = getGNewsInstance().getListOfHeadlinesURL(articlesList,Type.SOURCE_LINKS);
        List<String> listOfSourceNames = getGNewsInstance().getListOfHeadlinesURL(articlesList, Type.SOURCE_NAMES);
        sourceLink.setText(listOfSourceNames.get(indexPOS));
        sourceLink.setOnMouseEntered(mouseEvent -> sourceLink.setCursor(Cursor.OPEN_HAND));
        sourceLink.setOnMouseExited(mouseEvent -> sourceLink.setCursor(Cursor.DEFAULT));
        sourceLink.setOnMouseClicked(mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(listOfSourceLinks.get(indexPOS));
            alert.setHeaderText("Source link");
            alert.showAndWait();
        });
    }
    private void setArticleDescription(List<Articles> listOfArticles, int indexPOS) {
        textDescription.setText(listOfArticles.parallelStream().map((Articles::getDescription)).toList().get(indexPOS));
    }

    private int showNext(List<Articles> articlesList, int articleListLength) {
        indexCount++;
        setArticleImage(articlesList, indexCount);
        setArticleDescription(articlesList, indexCount);
        setSourceLink(articlesList,indexCount);
        if (indexCount >= articleListLength) {
            indexCount = 0;
        }
        return indexCount;
    }

    private int showPrevious(List<Articles> articlesList, int articleListLength) {
        indexCount--;
        setArticleImage(articlesList, indexCount);
        setArticleDescription(articlesList, indexCount);
        setSourceLink(articlesList,indexCount);
        if (indexCount < 0) {
            indexCount = articleListLength - 1;
        }
        return indexCount;
    }


}
