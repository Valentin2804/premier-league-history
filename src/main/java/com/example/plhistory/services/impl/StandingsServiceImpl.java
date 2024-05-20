package com.example.plhistory.services.impl;

import com.example.plhistory.entities.Standings;
import com.example.plhistory.entities.Teams;
import com.example.plhistory.repositories.StandingsRepository;
import com.example.plhistory.services.StandingsService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandingsServiceImpl implements StandingsService {

    private final StandingsRepository repository;

    public StandingsServiceImpl(StandingsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Standings getStandingByTeam(Teams team) {
        return repository.findByTeam(team);
    }

    @Override
    public List<Standings> getCurrentStanding() {

        Sort.Order points = new Sort.Order(Sort.Direction.DESC, "points");
        Sort.Order goalDifference = new Sort.Order(Sort.Direction.DESC, "goalDifference");
        Sort.Order goalsScored = new Sort.Order(Sort.Direction.DESC, "goalsScored");
        Sort.Order alphabet = new Sort.Order(Sort.Direction.DESC, "id");

        Sort sort = Sort.by(points, goalDifference, goalsScored, alphabet);

        return repository.findAll(sort);
    }
}
