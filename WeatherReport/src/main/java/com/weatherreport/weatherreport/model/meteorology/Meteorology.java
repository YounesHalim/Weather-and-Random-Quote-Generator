package com.weatherreport.weatherreport.model.meteorology;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meteorology {
    private Coordinate coordinate;
    private List<Weather> weather;
    private String base;
    private MainWeather main;
    private int visibility;
    private Wind wind;
    private Clouds cloud;
    private double dt;
    private Sys sys;
    private double timeZone;
    private double id;
    private String name;
    private double cod;

}



//{"coord":{"lon":-74.006,"lat":40.7143},
// "weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],
// "base":"stations",
// "main":{"temp":274.52,"feels_like":272.22,"temp_min":270.66,"temp_max":276.83,"pressure":1032,"humidity":64},
// "visibility":10000,
// "wind":{"speed":2.06,"deg":140},
// "clouds":{"all":0},
// "dt":1671675380,
// "sys":{"type":2,"id":2008776,"country":"US","sunrise":1671624997,"sunset":1671658296},
// "timezone":-18000,
// "id":5128581,
// "name":"New York",
// "cod":200}
