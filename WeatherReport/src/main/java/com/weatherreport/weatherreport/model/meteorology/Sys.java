package com.weatherreport.weatherreport.model.meteorology;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Sys {
    private int type, id;
    private String country;
    private long sunrise, sunset;
}
