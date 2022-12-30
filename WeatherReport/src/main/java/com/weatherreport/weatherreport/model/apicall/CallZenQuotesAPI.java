package com.weatherreport.weatherreport.model.apicall;


import com.weatherreport.weatherreport.model.Quotes.Quote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public interface CallZenQuotesAPI {
    default String getJSONAsAString() {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL("https://zenquotes.io/api/quotes/");
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

    Quote[] deserializedGsonObject();
}
