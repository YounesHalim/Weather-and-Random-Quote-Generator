package com.weatherreport.weatherreport.model.meteorology;

import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
class MeteorologyTest {

    @Test
    @DisplayName("API connection testing")
    void getJsonObject() throws IOException {
        String apiKey = "6607b5ff364b393169198f44f10e6715";
        String city = "Montreal";
        String country = "CA";
        String units = "metric";
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&appid=" + apiKey+"&units="+units);
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
  
}