package com.weatherreport.weatherreport.service;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.model.apicall.ApiCall;
import com.weatherreport.weatherreport.model.unsplash.Unsplash;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.SneakyThrows;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UnsplashService implements ApiCall {
    private static UnsplashService unsplashService;
    public static List<String> listOfURLs = new ArrayList<>();
    public UnsplashService() {
    }
    public static synchronized UnsplashService getApiUnsplashService() {
        if(unsplashService == null) {
            unsplashService = new UnsplashService();
        }
        return unsplashService;
    }


    public void setListOfURLs() {
        Unsplash unsplashObject = deserializedJSONObject();
        listOfURLs = unsplashObject
                .getResults()
                .parallelStream()
                .map((results -> results.getUrls().getRegular()+".jpeg"))
                .toList();
    }
    public static List<String> getListOfURLs() {
        return listOfURLs;
    }

    public void flushData() {
        File dir = new File("src/main/resources/com/weatherreport/weatherreport/thumbnail");
        if(Objects.requireNonNull(dir.listFiles()).length == 0) {
            return;
        }
        File[] files = dir.listFiles();
        assert files != null;
        for(File file: files) {
            file.delete();
        }
    }

    @Override
    public <T> T serializedJSONObject(T url) {
        return ApiCall.super.serializedJSONObject(url);
    }
    @SneakyThrows
    @Override
    public <T> T deserializedJSONObject() {
        String url = MessageFormat.format("https://api.unsplash.com/search/photos?page=10&query=outer-space&client_id={0}", Dotenv.load().get("APIKEY_UNSPLASH"));
        Gson gson = new Gson();
        Callable<Unsplash> unsplashCallable = () -> gson.fromJson(serializedJSONObject(url), Unsplash.class);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Unsplash> unsplashFuture = executorService.submit(unsplashCallable);
        return (T) unsplashFuture.get();
    }
}
