package com.weatherreport.weatherreport.model.apicall;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Singleton pattern
 */
public class CreateConnection {
    private static CreateConnection connectionInstance;
    private static HttpURLConnection httpURLConnection;
    private CreateConnection() {}
    public static synchronized CreateConnection getConnectionInstance() {
        if (connectionInstance == null) {
            connectionInstance = new CreateConnection();
        }
        return connectionInstance;
    }

    public HttpURLConnection getApiConnection(URL url) {
        try {
            if (this.httpURLConnection == null) httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return httpURLConnection;
    }

    public void closeConnection() {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }
}
