package com.weatherreport.weatherreport.model.apicall;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;

public interface CallUnsplashAPI {
//https://api.unsplash.com/collections/1111575/photos?client_id=Ez8EluKzO3ikTDUlfh8qQIBEXyWg63taNul6fryrxD0

    default String getJSONAsAString() {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(MessageFormat.format("https://api.unsplash.com/search/photos?page=10&query=outer-space&client_id={0}", Dotenv.load().get("APIKEY_UNSPLASH")));
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
            connection.disconnect();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return response.toString();
    }
    <T> T deserializedJsonObject();
}
