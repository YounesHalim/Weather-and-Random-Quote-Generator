package com.weatherreport.weatherreport.service;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.model.apicall.CallGNewsAPI;
import com.weatherreport.weatherreport.model.news.Articles;
import com.weatherreport.weatherreport.model.news.News;
import com.weatherreport.weatherreport.model.news.NewsObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
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
        switch (type) {
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
            case SOURCE_NAMES -> {
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

    public void thumbnailsDownloader(List<String> urls) throws IOException {
        int size = urls.size();
        int index = 0;
        File dir = new File("src/main/resources/com/weatherreport/weatherreport/thumbnail");
        if(Objects.requireNonNull(dir.listFiles()).length != 0) {
            return;
        }
        while (index < size) {
            URL url = new URL(urls.get(index));
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            File imgFile = new File(MessageFormat.format("src/main/resources/com/weatherreport/weatherreport/thumbnail/image{0}.jpg", index));
            try(OutputStream outputStream = new FileOutputStream(imgFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            inputStream.close();
            index++;
        }
    }

    public void fileDeleter() {
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
