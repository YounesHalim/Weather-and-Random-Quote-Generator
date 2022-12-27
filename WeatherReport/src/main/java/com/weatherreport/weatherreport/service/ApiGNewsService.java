package com.weatherreport.weatherreport.service;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.model.apicall.CallGNewsAPI;
import com.weatherreport.weatherreport.model.news.Articles;
import com.weatherreport.weatherreport.model.news.News;
import com.weatherreport.weatherreport.model.news.NewsObject;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ApiGNewsService implements CallGNewsAPI {
    private static ApiGNewsService ApiGNewsService;

    public enum Type {
        IMAGES,
        URLs,
        SOURCE_NAMES,
        SOURCE_LINKS
    }
    private ApiGNewsService() {
    }

    public static ApiGNewsService getGNewsInstance() {
        if (ApiGNewsService == null) {
            ApiGNewsService = new ApiGNewsService();
        }
        return ApiGNewsService;
    }

    public NewsObject deserializeGNewsJsonObject(News news) {
        Gson gson = new Gson();
        NewsObject newsObject = null;
        try {
            Callable<NewsObject> imageDataObjectCallable = () -> gson.fromJson(CallGNewsAPI.super.getJSONAsAString(news), NewsObject.class);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<NewsObject> imageDataFromFuture = executorService.submit(imageDataObjectCallable);
            newsObject = imageDataFromFuture.get();
            executorService.shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newsObject;
    }


    public List<String> getListOfHeadlinesURL(List<Articles> newsBatch, Type type) {
        switch (type){
            case IMAGES -> {
                return newsBatch
                        .parallelStream()
                        .map(Articles::getImage)
                        .toList();
            }
            case URLs -> {
                return newsBatch
                        .parallelStream()
                        .map(Articles::getUrl)
                        .toList();
            }
            case SOURCE_NAMES ->{
                return newsBatch
                        .parallelStream()
                        .map((articles -> articles.getSource().getName()))
                        .toList();
            }
            case SOURCE_LINKS -> {
                return newsBatch
                        .parallelStream()
                        .map((articles -> articles.getSource().getUrl()))
                        .toList();
            }
        }
        return null;
    }

}
