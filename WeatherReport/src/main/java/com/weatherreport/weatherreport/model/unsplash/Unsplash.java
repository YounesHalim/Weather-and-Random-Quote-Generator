package com.weatherreport.weatherreport.model.unsplash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Unsplash {
    private int total, total_pages;
    private List<Results> results;

}
