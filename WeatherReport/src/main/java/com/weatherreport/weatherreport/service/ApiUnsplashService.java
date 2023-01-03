package com.weatherreport.weatherreport.service;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.model.apicall.CallUnsplashAPI;
import com.weatherreport.weatherreport.model.unsplash.Unsplash;
import lombok.SneakyThrows;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ApiUnsplashService implements CallUnsplashAPI {
    private static ApiUnsplashService apiUnsplashService;
    public static List<String> listOfURLs = new ArrayList<>();
    public ApiUnsplashService() {
    }
    public static synchronized ApiUnsplashService getApiUnsplashService() {
        if(apiUnsplashService == null) {
            apiUnsplashService = new ApiUnsplashService();
        }
        return apiUnsplashService;
    }
    @Override
    public String getJSONAsAString() {
        return CallUnsplashAPI.super.getJSONAsAString();
    }
    @SneakyThrows
    @Override
    public <T> T deserializedJsonObject() {
        Gson gson = new Gson();
        Callable<Unsplash> unsplashCallable = () -> gson.fromJson(getJSONAsAString(), Unsplash.class);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Unsplash> unsplashFuture = executorService.submit(unsplashCallable);

        return (T) unsplashFuture.get();
    }

    public void setListOfURLs() {
        Unsplash unsplashObject = deserializedJsonObject();
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
}
