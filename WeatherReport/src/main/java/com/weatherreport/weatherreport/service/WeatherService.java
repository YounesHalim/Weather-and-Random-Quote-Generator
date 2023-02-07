package com.weatherreport.weatherreport.service;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.controllers.WeatherReportController;
import com.weatherreport.weatherreport.model.apicall.ApiCall;
import com.weatherreport.weatherreport.model.location.GeographicLocation;
import com.weatherreport.weatherreport.model.meteorology.Meteorology;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.*;

public class WeatherService implements ApiCall{
    private static WeatherService weatherService;
    public WeatherService() {}
    public static synchronized WeatherService getWeatherServiceInstance() {
        if (weatherService == null) {
            weatherService = new WeatherService();
        }
        return weatherService;
    }
    public Meteorology getMeteorologyObject(GeographicLocation cityLocation) {
        Meteorology meteorology;
        Gson gson = new Gson();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityLocation.getName() + "," + cityLocation.getCountry() + "&appid=" + "6607b5ff364b393169198f44f10e6715" + "&units=" + cityLocation.getMeasureUnits();
        try {
            Callable<Meteorology> meteorologyCallable = () -> gson.fromJson(serializedJSONObject(url), Meteorology.class);
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
        return new URL("https://openweathermap.org/img/wn/%s@2x.png".formatted(fetchedWeatherConditionIcon));
    }
    public Meteorology weatherApiCall(GeographicLocation location) {
        GeographicLocation news = GeographicLocation
                .builder()
                .name(location.getName())
                .country(location.getCountry())
                .measureUnits("metric")
                .build();

        return WeatherService
                .getWeatherServiceInstance()
                .getMeteorologyObject(news);
    }
    @Override
    public <T> T serializedJSONObject(T url) {
        return ApiCall.super.serializedJSONObject(url);
    }
    @Override
    public <T> T deserializedJSONObject() {
        return (T) getMeteorologyObject(WeatherReportController.defaultLocation);
    }
}
