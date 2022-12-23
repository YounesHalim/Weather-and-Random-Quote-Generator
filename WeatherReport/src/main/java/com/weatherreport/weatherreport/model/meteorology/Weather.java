package com.weatherreport.weatherreport.model.meteorology;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Weather {
    private int id;
    private String main,description,icon;
    public String getWeatherAttributes(Meteorology forecast, Function<Weather, String> mapper) {
        return forecast.getWeather().parallelStream().map(mapper).toList().get(0);
    }

}
