package com.weatherreport.weatherreport.service;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.model.apicall.ApiCall;
import com.weatherreport.weatherreport.model.unsplash.Unsplash;
import javafx.scene.image.Image;
import lombok.SneakyThrows;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

public class UnsplashService implements ApiCall {
    private static UnsplashService unsplashService;
    public static List<Image> listOfImageObjects = new ArrayList<>();
    private UnsplashService() {
    }
    public static synchronized UnsplashService getUnsplashInstance() {
        if (unsplashService == null) {
            unsplashService = new UnsplashService();
        }
        return unsplashService;
    }

    public static List<Image> getListOfImageObjects() {
        return listOfImageObjects;
    }

    public void flushData() {
        File dir = new File("src/main/resources/com/weatherreport/weatherreport/thumbnail");
        if (Objects.requireNonNull(dir.listFiles()).length == 0) {
            return;
        }
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
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
        return (T) CompletableFuture.supplyAsync(() -> {
                    String url = MessageFormat.format("https://api.unsplash.com/search/photos?page=10&query=outer-space&client_id={0}&w={1}&h={2}", "Ez8EluKzO3ikTDUlfh8qQIBEXyWg63taNul6fryrxD0", "574", "349");
                    return new Gson().fromJson(serializedJSONObject(url), Unsplash.class);
                })
                .thenApply(unsplashMappedObject -> {
                    listOfImageObjects = unsplashMappedObject.
                            getResults()
                            .parallelStream()
                            .map((image) -> new Image(image.getUrls().getRegular() + "jpeg"))
                            .toList();
                    return listOfImageObjects;
                }).exceptionally(error -> {
                    error.printStackTrace();
                    return null;
                }).get();
    }


}
