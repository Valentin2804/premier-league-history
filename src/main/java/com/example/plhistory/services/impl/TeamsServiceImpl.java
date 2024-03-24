package com.example.plhistory.services.impl;

import com.example.plhistory.entities.Matches;
import com.example.plhistory.entities.Teams;
import com.example.plhistory.repositories.TeamsRepository;
import com.example.plhistory.services.TeamsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamsServiceImpl implements TeamsService {

    private final TeamsRepository teamsRepository;

    private final MatchesServiceImpl service;

    public TeamsServiceImpl(TeamsRepository teamsRepository, MatchesServiceImpl service) {
        this.teamsRepository = teamsRepository;
        this.service = service;
    }

    @Override
    public List<Teams> getTeams(){
        return teamsRepository.findAll();
    }

    @Override
    public Teams getTeamByName(String name) {
        return teamsRepository.getTeamsByName(name);
    }

    @Override
    public String getStadiumByTeamName(String name) {
        return getTeamByName(name).getStadium();
    }

    @Override
    public Integer getStadiumCapacityByTeamName(String name) {
        return getTeamByName(name).getStadiumCapacity();
    }

    @Override
    public String getCityByTeamName(String name) {
        return getTeamByName(name).getCity();
    }

    @Override
    public List<Matches> getLastFiveHomeMatchesByTeamName(String name) {
        return service.getLastFiveHomeMatchesOfTeam(getTeamByName(name));
    }

    @Override
    public List<Matches> getLastFiveAwayMatchesByTeamName(String name) {
        return service.getLastFiveAwayMatchesOfTeam(getTeamByName(name));
    }

    @Override
    public List<Matches> getLastFiveMatchesByTeamName(String name) {
        return service.getLastFiveMatchesOfTeam(getTeamByName(name));
    }
}
