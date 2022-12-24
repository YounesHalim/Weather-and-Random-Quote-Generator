package com.weatherreport.weatherreport.service;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.model.apicall.CallUnsplashAPI;
import com.weatherreport.weatherreport.model.images.ImageDataObject;
import com.weatherreport.weatherreport.model.location.GeographicLocation;
import javafx.scene.image.Image;
import java.net.URL;

public class ApiUnsplashCallService implements CallUnsplashAPI {
    private static ApiUnsplashCallService apiUnsplashCallService;
    private ApiUnsplashCallService() {}

    public static ApiUnsplashCallService unsplashCallServiceInstance() {
        if(apiUnsplashCallService == null) {
            apiUnsplashCallService = new ApiUnsplashCallService();
        }
        return apiUnsplashCallService;
    }

    @Override
    public ImageDataObject deserializeUnsplashJsonObject(GeographicLocation location) {
        Gson gson = new Gson();
        return gson.fromJson(CallUnsplashAPI.super.getJSONAsAString(location), ImageDataObject.class);
    }

    @Override
    public Image getRandomImageBasedOnLocation(URL url) {
        return null;
    }
}
