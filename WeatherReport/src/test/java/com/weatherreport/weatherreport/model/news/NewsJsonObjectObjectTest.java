package com.weatherreport.weatherreport.model.news;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.weatherreport.weatherreport.model.location.GeographicLocation;
import com.weatherreport.weatherreport.service.ApiGNewsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

class NewsJsonObjectObjectTest {
    @Test
    @DisplayName("Connection to unsplash")
    void getConnectionResults() {

    }

    @Test
    @DisplayName("Parse JSON")
    void parseJSON() throws ExecutionException, InterruptedException {

    }

    @Test
    @DisplayName("Read JSON FILE")
    void JsonToHashTable() {
        String path = "src/main/resources/com/weatherreport/weatherreport/data.json";
        Hashtable<String, List<String>> countries = new Hashtable<>();
        Gson gson = new Gson();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            JsonObject countriesObject = gson.fromJson(bufferedReader, JsonObject.class);
            Optional<JsonElement> mappedCity = countriesObject.get("Canada")
                    .getAsJsonArray()
                    .asList()
                    .parallelStream()
                    .filter((city) -> city.getAsString().equals("Toronto"))
                    .findAny();

            System.out.println(mappedCity.isPresent());


//            JsonArray arr;
//            arr = (countriesObject.get("Morocco").getAsJsonArray());
//            for(JsonElement city: arr) {
//                if(city.getAsString().equals("Casablanca")){
//                    System.out.println(city.getAsString());
//                }
//            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("new JSON file testing")
    void displayJsonDATA() {
        String path = "src/main/resources/com/weatherreport/weatherreport/cities.json";
        Gson gson = new Gson();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            GeographicLocation data = gson.fromJson(bufferedReader, GeographicLocation.class);
            List<String> listOfCities = data.getData()
                    .parallelStream()
                    .map((city) -> city.getName() +", "+ city.getCountry())
                    .toList();

        listOfCities.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Deserializing JSON Object")
    void deserializedJSONTEST() {
        News news = News.builder().topic("breaking-news").language("EN").country("US").build();
        System.out.println(news.toString());
        NewsObject newsObject = ApiGNewsService.getGNewsInstance().deserializeGNewsJsonObject(news);

    }

    @Test
    @DisplayName("Testing URLS")
    void URLTester() {
        News news = News.builder().topic("breaking-news").language("EN").country("US").build();
        NewsObject newsObject = ApiGNewsService.getGNewsInstance().deserializeGNewsJsonObject(news);
        List<URL> newsUrls = ApiGNewsService.getGNewsInstance().getListOfHeadlinesURL(newsObject.getArticles(),(articles)-> {
            try {
                return new URL(articles.getUrl());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });

        newsUrls.forEach((url -> System.out.println(url.toString())));
    }



}