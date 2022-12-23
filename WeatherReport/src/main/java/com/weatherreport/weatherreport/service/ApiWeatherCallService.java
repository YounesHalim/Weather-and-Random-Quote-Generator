package com.weatherreport.weatherreport.service;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.model.apicall.CallOpenWeatherAPI;
import com.weatherreport.weatherreport.model.location.GeographicLocation;
import com.weatherreport.weatherreport.model.meteorology.*;

public class ApiWeatherCallService implements CallOpenWeatherAPI {
    private static ApiWeatherCallService apiWeatherCallService;
    private ApiWeatherCallService() {}
    public static ApiWeatherCallService getApiWeatherCallServiceInstance() {
        if(apiWeatherCallService == null) {
            apiWeatherCallService = new ApiWeatherCallService();
        }
        return apiWeatherCallService;
    }
    @Override
    public String getJSONAsAString(GeographicLocation location) {
        return CallOpenWeatherAPI.super.getJSONAsAString(location);
    }
    @Override
    public Meteorology getMeteorologyObject(GeographicLocation cityLocation) {
        Meteorology meteorology ;
        Gson gson = new Gson();
        meteorology = gson.fromJson(getJSONAsAString(cityLocation), Meteorology.class);
        return meteorology;
    }

}
