package com.weatherreport.weatherreport.model.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Articles {
    private String title, description, content, url, image, publishedAt;
    private Source source;
}
