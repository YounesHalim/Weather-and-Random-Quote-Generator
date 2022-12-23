package com.weatherreport.weatherreport.model.images;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {
    private String name, first_name, last_name, portfolio_url;
}
