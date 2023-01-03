package com.weatherreport.weatherreport.service;

import com.weatherreport.weatherreport.model.Quotes.Quote;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ZenQuotesServiceTest {
    @Test
    @DisplayName("API call test")
    void quoteCall() {
        Quote[] quoteObject = ZenQuotesService.getQuotesInstance().deserializedGsonObject();
        System.out.println(quoteObject[0].getA());
        System.out.println(quoteObject[0].getQ());
    }
}