package com.weatherreport.weatherreport.service;

import com.google.gson.Gson;
import com.weatherreport.weatherreport.model.Quotes.Quote;
import com.weatherreport.weatherreport.model.apicall.CallZenQuotesAPI;
import lombok.SneakyThrows;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ZenQuotesService implements CallZenQuotesAPI {
    private static ZenQuotesService quotesServiceInstance;
    private static final Quote[] quotes = getQuotesInstance().deserializedGsonObject();
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
    @Override
    public String getJSONAsAString() {
        return CallZenQuotesAPI.super.getJSONAsAString();
    }

    @SneakyThrows
    @Override
    public Quote[] deserializedGsonObject() {
        Gson gson  = new Gson();
        Callable<Quote[]> quotesObject = () -> gson.fromJson(getJSONAsAString(), Quote[].class);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Quote[]> quoteFuture = executorService.submit(quotesObject);

        return quoteFuture.get();
    }
    public static Quote[] getQuotes() {
        return quotes;
    }
}