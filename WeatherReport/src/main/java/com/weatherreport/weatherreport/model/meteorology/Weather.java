package com.weatherreport.weatherreport.model.meteorology;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Weather {
    private int id;
    private String main,description,icon;

}
