package com.weatherreport.weatherreport.service;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.model.Quotes.Quote;
import com.weatherreport.weatherreport.model.apicall.ApiCall;
import lombok.SneakyThrows;


import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
public class ZenQuotesService implements ApiCall {
    private static ZenQuotesService quotesServiceInstance;
    private static final Quote[] quotes = getQuotesInstance().deserializedJSONObject();

    public enum Type{
        QUOTE, AUTHOR, COUNT_CHAR, FORMATTED_HTML
    }
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
        Gson gson  = new Gson();
        Callable<Quote[]> quotesObject = () -> gson.fromJson(serializedJSONObject(new URL("https://zenquotes.io/api/quotes/").toString()), Quote[].class);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Quote[]> quoteFuture = executorService.submit(quotesObject);
        executorService.shutdown();
        return (T) quoteFuture.get();
    }

}
