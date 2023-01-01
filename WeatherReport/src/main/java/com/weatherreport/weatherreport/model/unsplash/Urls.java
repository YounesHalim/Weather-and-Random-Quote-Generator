package com.weatherreport.weatherreport.model.unsplash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Urls {
    private String id, color, description;
    private URL full, regular;
}
