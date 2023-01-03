package com.weatherreport.weatherreport.service;

import com.weatherreport.weatherreport.model.unsplash.Unsplash;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



class UnsplashServiceTest {

    @Test
    @DisplayName("Testing API CALL")
    void callAPI() {
        Unsplash unsplash = UnsplashService.getApiUnsplashService().deserializedJsonObject();
        System.out.println(unsplash.getTotal());
        System.out.println(unsplash.getTotal_pages());
        unsplash.getResults().forEach((element)-> System.out.println(element.getUrls().getFull()));
    }
}