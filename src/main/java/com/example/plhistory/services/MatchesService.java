package com.example.plhistory.services;

import com.example.plhistory.entities.Matches;
import com.example.plhistory.entities.Teams;

import java.util.List;

public interface MatchesService {

    List<Matches> getMatches();

    List<Matches> getLastFiveHomeMatchesOfTeam(Teams team);

    List<Matches> getLastFiveAwayMatchesOfTeam(Teams team);

    List<Matches> getLastFiveMatchesOfTeam(Teams team);
}
