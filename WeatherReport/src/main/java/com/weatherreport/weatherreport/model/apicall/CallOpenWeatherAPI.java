package com.weatherreport.weatherreport.model.apicall;

import com.weatherreport.weatherreport.model.location.GeographicLocation;
import com.weatherreport.weatherreport.model.meteorology.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public interface CallOpenWeatherAPI {
    default String getJSONAsAString(GeographicLocation location) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + location.getName() + "," + location.getCountry() + "&appid=" + Dotenv.load().get("APIKEY_OPEN_WEATHER") + "&units=" + location.getMeasureUnits());
            HttpURLConnection connection = CreateConnection
                    .getConnectionInstance()
                    .getApiConnection(url);


            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }finally {
            CreateConnection.getConnectionInstance().closeConnection();
        }
        return response.toString();
    }
    Meteorology getMeteorologyObject(GeographicLocation location);


}
