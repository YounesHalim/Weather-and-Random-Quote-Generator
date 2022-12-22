package com.weatherreport.weatherreport.service;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.model.apicall.CallOpenWeatherAPI;
import com.weatherreport.weatherreport.model.location.GeographicLocation;
import com.weatherreport.weatherreport.model.meteorology.*;

import java.util.List;

public class ApiWeatherCallService implements CallOpenWeatherAPI {
    private static Meteorology meteorology;
    @Override
    public String getJSONAsAString(GeographicLocation location) {
        return CallOpenWeatherAPI.super.getJSONAsAString(location);
    }

    @Override
    public Meteorology getMeteorologyObject(GeographicLocation cityLocation) {
        Gson gson = new Gson();
        meteorology = gson.fromJson(getJSONAsAString(cityLocation), Meteorology.class);
        return meteorology;
    }
    public static Meteorology getMeteorology() {
        if(meteorology == null) return null;
        return meteorology;
    }
}
