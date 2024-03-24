package com.example.plhistory.config.dataImportConfig.databaseImportConfig;

import com.example.plhistory.config.dataImportConfig.storageImportConfig.CloudManagement;
import com.example.plhistory.config.dataImportConfig.storageImportConfig.GoogleConnect;
import com.example.plhistory.entities.PremierLeague;
import com.example.plhistory.entities.Teams;
import com.example.plhistory.repositories.PremierLeagueRepository;
import com.example.plhistory.services.impl.TeamsServiceImpl;
import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.FileReader;
import java.io.IOException;

@Configuration
//@Order(5)
public class PremierLeagueImportConfig {

    private final TeamsServiceImpl service;

    public PremierLeagueImportConfig(TeamsServiceImpl service) {
        this.service = service;
    }

    @Transactional
    //@Bean
    CommandLineRunner fillPremierLeague(PremierLeagueRepository repository){
        return args -> {

            String plCsv = "src/main/resources/data/pl.csv";

            int indexOfWinner = 1;
            int indexOfRunnerUp = 3;
            int indexOfWinnerPoints = 2;
            int indexOfRunnerUpPoints = 4;
            int indexOfSeason = 0;

            try (CSVParser csvParser = CSVFormat.DEFAULT.parse(new FileReader(plCsv))) {
                for (CSVRecord record : csvParser) {

                    Teams winner = service.getTeamByName(record.get(indexOfWinner));
                    Teams runnerUp = service.getTeamByName(record.get(indexOfRunnerUp));
                    int winnerPoints = Integer.parseInt(record.get(indexOfWinnerPoints));
                    int runnerUpPoints = Integer.parseInt(record.get(indexOfRunnerUpPoints));
                    String season = record.get(indexOfSeason);

                    PremierLeague premierLeague = new PremierLeague();

                    premierLeague.setWinner(winner);
                    premierLeague.setSeason(season);
                    premierLeague.setRunnerUp(runnerUp);
                    premierLeague.setWinnerPoints(winnerPoints);
                    premierLeague.setRunnerUpPoints(runnerUpPoints);

                    repository.save(premierLeague);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
