package com.weatherreport.weatherreport.model.apicall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public interface ApiCall {
    default <T> T serializedJSONObject(T url) {
        StringBuilder response = new StringBuilder();
        try {
            URL apiURL = new URL((String) url);
            HttpURLConnection connection = CreateConnection
                    .getConnectionInstance()
                    .getApiConnection(apiURL);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return (T) response.toString();
    }

    <T> T deserializedJSONObject();

}
