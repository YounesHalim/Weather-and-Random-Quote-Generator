package com.weatherreport.weatherreport.service;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.model.apicall.CallOpenWeatherAPI;
import com.weatherreport.weatherreport.model.location.GeographicLocation;
import com.weatherreport.weatherreport.model.meteorology.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.*;

public class WeatherCallService implements CallOpenWeatherAPI {
    private static WeatherCallService weatherCallService;
    private WeatherCallService() {}
    public static WeatherCallService getApiWeatherCallServiceInstance() {
        if(weatherCallService == null) {
            weatherCallService = new WeatherCallService();
        }
        return weatherCallService;
    }
    @Override
    public String getJSONAsAString(GeographicLocation location) {
        return CallOpenWeatherAPI.super.getJSONAsAString(location);
    }
    @Override
    public Meteorology getMeteorologyObject(GeographicLocation cityLocation) {
        Meteorology meteorology;
        Gson gson = new Gson();
        try {
            Callable<Meteorology> meteorologyCallable = () -> gson.fromJson(getJSONAsAString(cityLocation), Meteorology.class);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<Meteorology> meteorologyFuture = executorService.submit(meteorologyCallable);
            meteorology = meteorologyFuture.get();
            executorService.shutdown();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return meteorology;
    }
    public URL getAppropriateWeatherIcon(String fetchedWeatherConditionIcon) throws MalformedURLException {
        return new URL("http://openweathermap.org/img/wn/%s@2x.png".formatted(fetchedWeatherConditionIcon));
    }
    public Meteorology weatherApiCall(GeographicLocation location) {
        GeographicLocation news = GeographicLocation
                .builder()
                .name(location.getName())
                .country(location.getCountry())
                .measureUnits("metric")
                .build();

        return WeatherCallService
                .getApiWeatherCallServiceInstance()
                .getMeteorologyObject(news);
    }

}
