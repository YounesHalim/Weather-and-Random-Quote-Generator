package com.weatherreport.weatherreport.model.apicall;

import com.weatherreport.weatherreport.model.news.News;
import com.weatherreport.weatherreport.model.news.NewsObject;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public interface CallGNewsAPI {
    default String getJSONAsAString(News news) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL("https://gnews.io/api/v4/top-headlines?topic=" + news.getTopic() +"&token=" + Dotenv.load().get("APIKEY_NEWS") +"&lang="+news.getLanguage()+"&country="+news.getCountry());
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
    NewsObject deserializeGNewsJsonObject(News news);


}
