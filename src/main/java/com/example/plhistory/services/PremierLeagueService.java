package com.example.plhistory.services;

import com.example.plhistory.entities.PremierLeague;
import com.example.plhistory.entities.Teams;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface PremierLeagueService {

    List<PremierLeague> getPremierLeagues();

    Integer getTitlesByTeam(Teams team);
}
