package com.example.plhistory.services.impl;

import com.example.plhistory.entities.PremierLeague;
import com.example.plhistory.entities.Teams;
import com.example.plhistory.repositories.PremierLeagueRepository;
import com.example.plhistory.services.PremierLeagueService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PremierLeagueServiceImpl implements PremierLeagueService {

    private final PremierLeagueRepository repository;

    public PremierLeagueServiceImpl(PremierLeagueRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PremierLeague> getPremierLeagues() {
        return repository.findAll();
    }

    @Override
    public Integer getTitlesByTeam(Teams team) {
        return repository.countAllByWinner(team);
    }
}
