package com.example.plhistory.config.dataImportConfig.databaseImportConfig;

import com.example.plhistory.entities.Matches;
import com.example.plhistory.entities.Teams;
import com.example.plhistory.repositories.MatchesRepository;
import com.example.plhistory.services.TeamsService;
import com.example.plhistory.services.impl.MatchesServiceImpl;
import com.opencsv.CSVReader;
import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Configuration
//@Order(4)
public class MatchesImportConfig {

    private final TeamsService service;
    private final MatchesServiceImpl matchesService;

    public MatchesImportConfig(TeamsService service, MatchesServiceImpl matchesService) {
        this.service = service;
        this.matchesService = matchesService;
    }

    @Transactional
    //@Bean
    //@Order(1)
    public CommandLineRunner fillOldMatches(MatchesRepository repository){
        return args -> {

            String matchesCsv = "src/main/resources/data/match.csv";

            String clubsCsv = "src/main/resources/data/club.csv";

            HashMap<String, String> clubIdToName = new HashMap<>();

            int indexOfClubId = 0;
            int indexOfClubName = 2;

            try (CSVParser csvParser = CSVFormat.DEFAULT.parse(new FileReader(clubsCsv))) {
                for (CSVRecord record : csvParser) {
                    if(!record.get(indexOfClubId).equals("club_id")) {
                        String clubId = record.get(indexOfClubId);
                        String clubName = record.get(indexOfClubName);
                        clubIdToName.put(clubId, clubName);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            int indexOfMatchId = 0;
            int indexOfHomeTeamIdForMatch = 1;
            int indexOfAwayTeamIdForMatch = 2;
            int indexOfHomeTeamGoals = 3;
            int indexOfAwayTeamGoals = 4;
            int indexOfDate = 5;

            try (CSVParser csvParser = CSVFormat.DEFAULT.parse(new FileReader(matchesCsv))) {

                for (CSVRecord record : csvParser) {
                    if(!record.get(indexOfMatchId).equals("match_id")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                        Teams homeTeam = service.getTeamByName(clubIdToName.get(record.get(indexOfHomeTeamIdForMatch)));
                        Teams awayTeam = service.getTeamByName(clubIdToName.get(record.get(indexOfAwayTeamIdForMatch)));
                        int homeTeamGoals = Integer.parseInt(record.get(indexOfHomeTeamGoals));
                        int awayTeamGoals = Integer.parseInt(record.get(indexOfAwayTeamGoals));
                        LocalDate date = null;
                        if(!record.get(indexOfDate).equals("0000-00-00")){
                            date = LocalDate.parse(record.get(indexOfDate), formatter);
                        }

                        Matches match = new Matches();
                        match.setDate(date);
                        match.setHomeTeam(homeTeam);
                        match.setAwayTeam(awayTeam);
                        match.setHomeTeamGoals(homeTeamGoals);
                        match.setAwayTeamGoals(awayTeamGoals);

                        repository.save(match);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    @Transactional
    //@Bean
    //@Order(2)
    CommandLineRunner fillMatches(MatchesRepository repository){
        return args -> {

            String matchesCsv = "src/main/resources/data/premier_league_last_two_seasons_matches.csv";

            int indexOfSeasonEndYear = 0;
            int indexOfHomeTeam = 3;
            int indexOfAwayTeam = 6;
            int indexOfHomeTeamGoals = 4;
            int indexOfAwayTeamGoals = 5;
            int indexOfDate = 2;

            try (CSVParser csvParser = CSVFormat.DEFAULT.parse(new FileReader(matchesCsv))) {

                for (CSVRecord record : csvParser) {
                    if(!record.get(indexOfSeasonEndYear).equals("Season_End_Year")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                        Teams homeTeam = service.getTeamByName(record.get(indexOfHomeTeam));
                        Teams awayTeam = service.getTeamByName(record.get(indexOfAwayTeam));
                        int homeTeamGoals = Integer.parseInt(record.get(indexOfHomeTeamGoals));
                        int awayTeamGoals = Integer.parseInt(record.get(indexOfAwayTeamGoals));
                        LocalDate date = LocalDate.parse(record.get(indexOfDate), formatter);


                        Matches match = new Matches();
                        match.setDate(date);
                        match.setHomeTeam(homeTeam);
                        match.setAwayTeam(awayTeam);
                        match.setHomeTeamGoals(homeTeamGoals);
                        match.setAwayTeamGoals(awayTeamGoals);

                        repository.save(match);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    CommandLineRunner fillLastSeasonMatches(){
        return args -> {
            String matchesCsv = "src/main/resources/data/matches.csv";

            try (CSVReader reader = new CSVReader(new FileReader(matchesCsv))) {
                List<String[]> rows = reader.readAll();
                Map<String, Matches> uniqueMatches = new HashMap<>();

                for (int i = 1; i < rows.size(); i++) {
                    String[] row = rows.get(i);
                    String date = row[0];
                    String venue = row[1];
                    String gf = row[2];
                    String ga = row[3];
                    String opponent = row[4];
                    String team = row[5];

                    String homeTeam;
                    String awayTeam;
                    int homeTeamGoals;
                    int awayTeamGoals;

                    if ("Away".equalsIgnoreCase(venue)) {
                        awayTeam = team;
                        awayTeamGoals = Integer.parseInt(gf);
                        homeTeam = opponent;
                        homeTeamGoals = Integer.parseInt(ga);
                    } else {
                        homeTeam = team;
                        homeTeamGoals = Integer.parseInt(gf);
                        awayTeam = opponent;
                        awayTeamGoals = Integer.parseInt(ga);
                    }

                    LocalDate dateOfMatch = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    String matchKey = generateMatchKey(dateOfMatch, homeTeam, awayTeam);

                    Teams homeTeamObj = service.getTeamByName(homeTeam);
                    Teams awayTeamObj = service.getTeamByName(awayTeam);

                    if(!uniqueMatches.containsKey(matchKey)) {
                        Matches match = new Matches();
                        match.setAwayTeam(awayTeamObj);
                        match.setHomeTeam(homeTeamObj);
                        match.setHomeTeamGoals(homeTeamGoals);
                        match.setAwayTeamGoals(awayTeamGoals);
                        match.setDate(dateOfMatch);

                        uniqueMatches.put(matchKey, match);
                        matchesService.insertMatch(match);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private String generateMatchKey(LocalDate date, String homeTeam, String awayTeam) {
        List<String> teams = Arrays.asList(homeTeam, awayTeam);
        Collections.sort(teams);
        return date.toString() + "-" + teams.get(0) + "-" + teams.get(1);
    }
}
