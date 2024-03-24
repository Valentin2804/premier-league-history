package com.example.plhistory.config;

import lombok.Data;

@Data
public class News {
    private final String headline;
    private final String imageLink;
    private final String introducingSentence;
    private final String publishMoment;
}
