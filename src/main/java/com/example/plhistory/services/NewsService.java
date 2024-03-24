package com.example.plhistory.services;

import com.example.plhistory.config.News;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface NewsService {

    List<News> getTeamNews(String team) throws IOException;

    String getTeamNewsURI(String team);
}
