package com.weatherreport.weatherreport.model.meteorology;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.model.location.GeographicLocation;
import com.weatherreport.weatherreport.service.WeatherService;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.text.MessageFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

class MeteorologyTest {
    @Test
    @DisplayName("API connection testing")
    void getJsonObject() throws IOException {
        String apiKey = "6607b5ff364b393169198f44f10e6715";
        String city = "Montreal";
        String country = "CA";
        String units = "metric";
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&appid=" + apiKey + "&units=" + units);
        // https://openweathermap.org/weather-conditions#Weather-Condition-Codes-2
        // Open a connection to the API endpoint
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to GET
        connection.setRequestMethod("GET");

        // Set the Accept header
        connection.setRequestProperty("Accept", "application/json");

        // Send the request and retrieve the response
        BufferedReader reader = new BufferedReader
                (new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Parse the response
        Gson gson = new Gson();
        final Meteorology meteorology = gson.fromJson(String.valueOf(response), Meteorology.class);
        System.out.println(meteorology.toString());
        System.out.println(meteorology.getSys().getCountry());
        System.out.println(meteorology.getSys().getSunrise());
        System.out.println(meteorology.getSys().getSunset());
    }

    @Test
    @DisplayName("Testing the .env file")
    void dotEnvTesting() {
        String apiKey = Dotenv.load().get("APIKEY");
        System.out.println(apiKey);
    }

    @Test
    @DisplayName("Testing weather report")
    void getDataJson() {
        GeographicLocation defaultGeoLocation = GeographicLocation
                .builder()
                .name("Casablanca")
                .country("MA")
                .measureUnits("metric")
                .build();
        Meteorology forecast = WeatherService
                .getWeatherServiceInstance()
                .getMeteorologyObject(defaultGeoLocation);

        String cityName = forecast.getName();
        String countryName = forecast.getSys().getCountry().toUpperCase();
        int feels_like = (int) (Math.floor(forecast.getMain().getFeels_like()));
        int temp = (int) Math.floor(forecast.getMain().getTemp());
        final Date date = new Date(forecast.getSys().getSunrise() * 1000);

        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        String timeZone = zoneIds.parallelStream().filter((zone)->zone.contains(cityName)).findFirst().orElse(null);

        if(timeZone.split("/")[0].equalsIgnoreCase("africa")) {

        }
        System.out.println(new Date(forecast.getSys().getSunrise() * 1000));
        System.out.println(new Date(forecast.getSys().getSunset() * 1000));

        System.out.println(MessageFormat.format("Time of calculation: {0}",new Date((long) (forecast.getDt() * 1000))));
        System.out.println(new Date((long) forecast.getTimeZone() * 100));
    }

}