package com.example.plhistory.services.impl;

import com.example.plhistory.entities.Matches;
import com.example.plhistory.entities.Standings;
import com.example.plhistory.entities.Teams;
import com.example.plhistory.repositories.MatchesRepository;
import com.example.plhistory.repositories.StandingsRepository;
import com.example.plhistory.services.MatchesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchesServiceImpl implements MatchesService {
    private final MatchesRepository repository;

    private final StandingsRepository standingsRepository;

    private final StandingsServiceImpl service;

    public MatchesServiceImpl(MatchesRepository repository, StandingsRepository standingsRepository, StandingsServiceImpl service) {
        this.repository = repository;
        this.standingsRepository = standingsRepository;
        this.service = service;
    }

    @Override
    public List<Matches> getMatches() {
        return repository.findAll();
    }

    @Override
    public List<Matches> getLastFiveHomeMatchesOfTeam(Teams team) {
        return repository.findTop5ByHomeTeamOrderByDateDesc(team);
    }

    @Override
    public List<Matches> getLastFiveAwayMatchesOfTeam(Teams team) {
        return repository.findTop5ByAwayTeamOrderByDateDesc(team);
    }

    @Override
    public List<Matches> getLastFiveMatchesOfTeam(Teams team) {
        return repository.findTop5ByHomeTeamOrAwayTeamOrderByDateDesc(team, team);
    }

    public void insertMatch(Matches match){

        Standings homeTeamStanding = service.getStandingByTeam(match.getHomeTeam());
        Standings awayTeamStanding = service.getStandingByTeam(match.getAwayTeam());

        homeTeamStanding.setMatchesPlayed(homeTeamStanding.getMatchesPlayed() + 1);
        awayTeamStanding.setMatchesPlayed(homeTeamStanding.getMatchesPlayed() + 1);

        homeTeamStanding.setGoalsScored(homeTeamStanding.getGoalsScored() + match.getHomeTeamGoals());
        awayTeamStanding.setGoalsScored(awayTeamStanding.getGoalsScored() + match.getAwayTeamGoals());

        homeTeamStanding.setGoalsConceded(homeTeamStanding.getGoalsConceded() + match.getAwayTeamGoals());
        awayTeamStanding.setGoalsConceded(awayTeamStanding.getGoalsConceded() + match.getHomeTeamGoals());

        homeTeamStanding.setGoalDifference(homeTeamStanding.getGoalDifference() + match.getHomeTeamGoals() - match.getAwayTeamGoals());
        awayTeamStanding.setGoalDifference(awayTeamStanding.getGoalDifference() + match.getAwayTeamGoals() - match.getHomeTeamGoals());

        if(match.getHomeTeamGoals() > match.getAwayTeamGoals()){
            homeTeamStanding.setPoints(homeTeamStanding.getPoints() + 3);
        } else if (match.getAwayTeamGoals() > match.getHomeTeamGoals()) {
            homeTeamStanding.setPoints(homeTeamStanding.getPoints() + 3);
        }else {
            homeTeamStanding.setPoints(homeTeamStanding.getPoints() + 1);
            homeTeamStanding.setPoints(homeTeamStanding.getPoints() + 1);
        }

        standingsRepository.save(homeTeamStanding);
        standingsRepository.save(awayTeamStanding);

        repository.save(match);
    }
}
