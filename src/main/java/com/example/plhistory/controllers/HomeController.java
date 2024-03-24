package com.example.plhistory.controllers;

import com.example.plhistory.config.News;
import com.example.plhistory.config.dataImportConfig.storageImportConfig.CloudManagement;
import com.example.plhistory.controllers.requests.SearchRequest;
import com.example.plhistory.entities.Matches;
import com.example.plhistory.entities.Standings;
import com.example.plhistory.entities.Teams;
import com.example.plhistory.services.impl.NewsServiceImpl;
import com.example.plhistory.services.impl.PremierLeagueServiceImpl;
import com.example.plhistory.services.impl.StandingsServiceImpl;
import com.example.plhistory.services.impl.TeamsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    private final CloudManagement cloud = new CloudManagement();

    private final TeamsServiceImpl service;

    private final StandingsServiceImpl standings;

    private final PremierLeagueServiceImpl premierLeague;

    private final NewsServiceImpl news;

    public HomeController(TeamsServiceImpl service, StandingsServiceImpl standings, PremierLeagueServiceImpl premierLeague, NewsServiceImpl news) {
        this.service = service;
        this.standings = standings;
        this.premierLeague = premierLeague;
        this.news = news;
    }

    @GetMapping("/home")
    public String getView(@ModelAttribute("search")SearchRequest search){
        return "home";
    }

    @PostMapping("/home")
    public String search(@ModelAttribute("search")SearchRequest search, RedirectAttributes attributes){

        attributes.addAttribute("homeTeam", search.getHomeTeam());
        attributes.addAttribute("awayTeam", search.getAwayTeam());

        return "redirect:/result";
    }

    @GetMapping("/redirect")
    public String redirectToAnotherPage() {
        return "redirect:/result";
    }

    @GetMapping("/result")
    public String targetPage(@RequestParam("homeTeam") String homeTeam,
                             @RequestParam("awayTeam") String awayTeam,
                             Model model) {
        model.addAttribute("homeTeam", homeTeam);
        model.addAttribute("awayTeam", awayTeam);

        Teams home = service.getTeamByName(homeTeam);
        Teams away = service.getTeamByName(awayTeam);

        String stadium = service.getStadiumByTeamName(homeTeam);
        model.addAttribute("stadiumName", stadium);
        model.addAttribute("capacity", service.getStadiumCapacityByTeamName(homeTeam));
        model.addAttribute("city", service.getCityByTeamName(homeTeam));

        String stadiumImage = cloud.download(stadium.replace(" ", "_"), "stadiums");
        String homeTeamLogo = cloud.download(homeTeam.replace(" ", "_"), "teams");
        String awayTeamLogo = cloud.download(awayTeam.replace(" ", "_"), "teams");

        model.addAttribute("stadiumPhoto", "data:image/png;base64," + stadiumImage);
        model.addAttribute("homeTeamLogo", "data:image/png;base64," + homeTeamLogo);
        model.addAttribute("awayTeamLogo", "data:image/png;base64," + awayTeamLogo);

        List<Matches> matches;
        matches = service.getLastFiveHomeMatchesByTeamName(homeTeam);
        model.addAttribute("lastFiveHomeTeamHomeMatches", matches);
        matches = service.getLastFiveAwayMatchesByTeamName(awayTeam);
        model.addAttribute("lastFiveAwayTeamAwayMatches", matches);

        List<Standings> currentStandings;
        currentStandings = standings.getCurrentStanding();
        model.addAttribute("standingsList", currentStandings);

        model.addAttribute("homeTitles", premierLeague.getTitlesByTeam(home));
        model.addAttribute("awayTitles", premierLeague.getTitlesByTeam(away));

        String homeTeamNewsURI = news.getTeamNewsURI(homeTeam);
        String awayTeamNewsURI = news.getTeamNewsURI(awayTeam);
        List<News> homeTeamNews;
        List<News> awayTeamNews;

        try {
            homeTeamNews = news.getTeamNews(homeTeamNewsURI);
            awayTeamNews = news.getTeamNews(awayTeamNewsURI);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        model.addAttribute("homeTeamNewsItems", homeTeamNews);
        model.addAttribute("awayTeamNewsItems", awayTeamNews);

        model.addAttribute("homeFullArticlesLink", homeTeamNewsURI);
        model.addAttribute("awayFullArticlesLink", awayTeamNewsURI);

        model.addAttribute("homeOfficialWebsiteLink", "https://" + home.getWebsite());
        model.addAttribute("awayOfficialWebsiteLink", "https://" + away.getWebsite());

        return "result";
    }

}
