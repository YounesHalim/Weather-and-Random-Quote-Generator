package com.weatherreport.weatherreport.service;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.model.apicall.CallGNewsAPI;
import com.weatherreport.weatherreport.model.news.Articles;
import com.weatherreport.weatherreport.model.news.News;
import com.weatherreport.weatherreport.model.news.NewsObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ApiGNewsService implements CallGNewsAPI {
    private static ApiGNewsService ApiGNewsService;

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

    public List<URL> getListOfHeadlinesURL(List<Articles> newsBatch, Function<Articles, URL> mapper) {
        return newsBatch.parallelStream().map(mapper).toList();
    }

}
