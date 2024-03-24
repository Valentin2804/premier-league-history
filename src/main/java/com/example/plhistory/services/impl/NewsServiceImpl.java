package com.example.plhistory.services.impl;

import com.example.plhistory.config.News;
import com.example.plhistory.services.NewsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Override
    public List<News> getTeamNews(String URIWithNews) throws IOException {

        int indexOfStart = 0;
        int indexOfEndArticlesList = 3;
        int indexOfBeginCreationMoment = 12;
        int lengthOfUsefulPart = 13;
        int duplicatedString = 2;

        Document document = Jsoup.connect(URIWithNews).get();

        Elements elements = document.select("article");

        List<Element> firstThreeArticles = elements.subList(indexOfStart, indexOfEndArticlesList);

        List<News> newsList = new ArrayList<>();
        for (Element article : firstThreeArticles) {

            Element header = article.selectFirst("header");
            Element firstImage = article.selectFirst("figure img");
            String firstSentence = article.selectFirst("p").text();

            int index = header.text().indexOf("published at");
            String publishMoment = header.text().substring(index);
            String extractFromFirstSentence = publishMoment.substring(indexOfBeginCreationMoment);
            publishMoment = publishMoment.substring
                    (indexOfStart, publishMoment.length() - (publishMoment.length() - lengthOfUsefulPart)/duplicatedString);
            String title = header.text().substring(index, index);

            firstSentence = firstSentence.replace(extractFromFirstSentence, "");

            News news = new News(title, firstImage.attr("src"), firstSentence, publishMoment);
            newsList.add(news);
        }

        return newsList;
    }

    @Override
    public String getTeamNewsURI(String team) {
        return "https://www.bbc.com/sport/football/teams/"
                + team.replace(" ", "-").toLowerCase();
    }
}
