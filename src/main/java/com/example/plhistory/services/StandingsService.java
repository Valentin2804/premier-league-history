package com.example.plhistory.services;

import com.example.plhistory.entities.Standings;
import com.example.plhistory.entities.Teams;

import java.util.List;

public interface StandingsService {
    Standings getStandingByTeam(Teams team);

    List<Standings> getCurrentStanding();
}
