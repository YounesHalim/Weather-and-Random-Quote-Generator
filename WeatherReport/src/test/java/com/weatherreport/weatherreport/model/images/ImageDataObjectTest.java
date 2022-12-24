package com.weatherreport.weatherreport.model.images;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.weatherreport.weatherreport.model.location.GeographicLocation;
import com.weatherreport.weatherreport.service.ApiUnsplashCallService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class ImageDataObjectTest {
    @Test
    @DisplayName("Connection to unsplash")
    void getConnectionResults() {
        GeographicLocation city = GeographicLocation.builder().name("Montreal").build();
        String jsonAsAString = ApiUnsplashCallService.unsplashCallServiceInstance().getJSONAsAString(city);
        System.out.println(jsonAsAString);
    }

    @Test
    @DisplayName("Parse JSON")
    void parseJSON() {
        GeographicLocation city = GeographicLocation.builder().name("Toronto-city").build();
        ImageDataObject imageDataObject = ApiUnsplashCallService.unsplashCallServiceInstance().deserializeUnsplashJsonObject(city);
        imageDataObject.getResults().parallelStream().forEach(System.out::println);
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


}