package com.weatherreport.weatherreport.service;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.model.Quotes.Quote;
import com.weatherreport.weatherreport.model.apicall.ApiCall;
import lombok.SneakyThrows;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.*;

public class ZenQuotesService implements ApiCall {
    private static ZenQuotesService quotesServiceInstance;
    private static final Quote[] quotes = getQuotesInstance().deserializedJSONObject();
    private ZenQuotesService() {}

    public static synchronized ZenQuotesService getQuotesInstance() {
        if(quotesServiceInstance == null) {
            quotesServiceInstance = new ZenQuotesService();
        }
        return quotesServiceInstance;
    }
    public static Quote[] getQuotes() {
        return quotes;
    }
    @Override
    public <T> T serializedJSONObject(T url) {
        return ApiCall.super.serializedJSONObject(url);
    }
    @SneakyThrows
    @Override
    public <T> T  deserializedJSONObject() {
        return (T) CompletableFuture.supplyAsync(()-> {
            try {
                return new Gson().fromJson(serializedJSONObject(new URL("https://zenquotes.io/api/quotes/").toString()),Quote[].class);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }).exceptionally((error) -> {
            System.out.println(error.getMessage());
            return null;
        }).get();
    }

    public Optional<String> getHTML(String quote) {
        return quote.describeConstable();
    }

}
