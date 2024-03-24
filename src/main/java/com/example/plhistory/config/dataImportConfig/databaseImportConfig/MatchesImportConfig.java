package com.example.plhistory.config.dataImportConfig.databaseImportConfig;

import com.example.plhistory.entities.Matches;
import com.example.plhistory.entities.Teams;
import com.example.plhistory.repositories.MatchesRepository;
import com.example.plhistory.services.TeamsService;
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
import java.util.HashMap;

@Configuration
//@Order(4)
public class MatchesImportConfig {

    private final TeamsService service;

    public MatchesImportConfig(TeamsService service) {
        this.service = service;
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
}
