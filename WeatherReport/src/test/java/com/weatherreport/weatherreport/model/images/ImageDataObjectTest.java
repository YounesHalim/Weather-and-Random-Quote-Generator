package com.weatherreport.weatherreport.model.images;

import com.weatherreport.weatherreport.model.location.GeographicLocation;
import com.weatherreport.weatherreport.service.ApiUnsplashCallService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class ImageDataObjectTest {
    @Test
    @DisplayName("Connection to unsplash")
    void getConnectionResults() {
        GeographicLocation city = GeographicLocation.builder().city("Montreal").build();
        String jsonAsAString = ApiUnsplashCallService.unsplashCallServiceInstance().getJSONAsAString(city);
        System.out.println(jsonAsAString);
    }
    @Test
    @DisplayName("Parse JSON")
    void parseJSON() {
        GeographicLocation city = GeographicLocation.builder().city("Toronto-city").build();
        ImageDataObject imageDataObject = ApiUnsplashCallService.unsplashCallServiceInstance().deserializeUnsplashJsonObject(city);
        imageDataObject.getResults().parallelStream().forEach(System.out::println);
    }

}