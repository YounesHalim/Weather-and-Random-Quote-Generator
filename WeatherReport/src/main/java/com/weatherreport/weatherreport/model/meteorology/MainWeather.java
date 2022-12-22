package com.weatherreport.weatherreport.model.meteorology;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MainWeather {
    private double temp, feels_like, temp_min, temp_max,pressure,humidity;
}
