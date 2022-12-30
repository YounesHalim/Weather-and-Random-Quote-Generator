package com.weatherreport.weatherreport.service;

import com.google.gson.JsonObject;
import com.weatherreport.weatherreport.model.Quotes.Quote;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ApiZenQuotesServiceTest {
    @Test
    @DisplayName("API call test")
    void quoteCall() {
        Quote[] quoteObject = ApiZenQuotesService.getQuotesInstance().deserializedGsonObject();
        System.out.println(quoteObject[0].getA());
        System.out.println(quoteObject[0].getQ());
    }
}