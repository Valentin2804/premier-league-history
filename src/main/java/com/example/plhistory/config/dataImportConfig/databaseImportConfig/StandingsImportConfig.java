package com.example.plhistory.config.dataImportConfig.databaseImportConfig;

import com.example.plhistory.entities.Standings;
import com.example.plhistory.entities.Teams;
import com.example.plhistory.repositories.StandingsRepository;
import com.example.plhistory.services.impl.TeamsServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Configuration
//@Order(6)
public class StandingsImportConfig {

    private final TeamsServiceImpl service;

    private final StandingsRepository repository;

    public StandingsImportConfig(TeamsServiceImpl service, StandingsRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    //@Bean
    public CommandLineRunner fillStandings(){
        return args -> {
            List<Teams> teams = new ArrayList<>();
            teams.add(service.getTeamByName("Arsenal"));
            teams.add(service.getTeamByName("Aston Villa"));
            teams.add(service.getTeamByName("AFC Bournemouth"));
            teams.add(service.getTeamByName("Brentford"));
            teams.add(service.getTeamByName("Brighton and Hove Albion"));
            teams.add(service.getTeamByName("Burnley"));
            teams.add(service.getTeamByName("Chelsea"));
            teams.add(service.getTeamByName("Crystal Palace"));
            teams.add(service.getTeamByName("Everton"));
            teams.add(service.getTeamByName("Fulham"));
            teams.add(service.getTeamByName("Liverpool"));
            teams.add(service.getTeamByName("Luton Town"));
            teams.add(service.getTeamByName("Manchester City"));
            teams.add(service.getTeamByName("Manchester United"));
            teams.add(service.getTeamByName("Newcastle United"));
            teams.add(service.getTeamByName("Nottingham Forest"));
            teams.add(service.getTeamByName("Sheffield United"));
            teams.add(service.getTeamByName("Tottenham Hotspur"));
            teams.add(service.getTeamByName("West Ham United"));
            teams.add(service.getTeamByName("Wolverhampton Wanderers"));

            int startValueForEveryCell = 0;

            for (Teams team : teams){
                Standings standing = new Standings();
                standing.setPoints(startValueForEveryCell);
                standing.setGoalsScored(startValueForEveryCell);
                standing.setGoalsConceded(startValueForEveryCell);
                standing.setMatchesPlayed(startValueForEveryCell);
                standing.setGoalDifference(startValueForEveryCell);
                standing.setTeam(team);

                repository.save(standing);
            }
        };
    }
}
