package com.weatherreport.weatherreport.model.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsObject {
    private int totalArticles;
    private List<Articles> articles;
}

