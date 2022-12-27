package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.model.news.Articles;
import com.weatherreport.weatherreport.model.news.News;
import com.weatherreport.weatherreport.model.news.NewsObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

import static com.weatherreport.weatherreport.service.ApiGNewsService.*;

public class NewsReportsController implements Initializable {

    @FXML private Rectangle mainImageArticle;
    public static News defaultNews = News.builder().language("EN").topic("breaking-news").country("CA").build();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NewsObject newsObject = setNewsApiCall(defaultNews);
        List<Articles> articlesList = newsObject.getArticles();
        setArticleImage(articlesList);
    }
    protected NewsObject setNewsApiCall(News news) {
        return getGNewsInstance().deserializeGNewsJsonObject(news);
    }

    private void setArticleImage(List<Articles> listOfArticles) {
        List<String> listOfImages = getGNewsInstance().getListOfHeadlinesURL(listOfArticles,"IMAGES");
        int size = listOfImages.size();
        if(size == 0) {
            return;
        }
        mainImageArticle.setFill(new ImagePattern(new Image(Objects.requireNonNull(listOfImages.get(new Random().nextInt(0,size))))));
        mainImageArticle.setArcHeight(20);
        mainImageArticle.setArcWidth(20);
    }
}
