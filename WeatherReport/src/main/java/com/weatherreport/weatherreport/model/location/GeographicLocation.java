package com.weatherreport.weatherreport.model.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeographicLocation {
    private String country, name, lat, lng, measureUnits;
    private List<GeographicLocation> data;
}
