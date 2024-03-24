package com.example.plhistory.services;

import com.example.plhistory.entities.Matches;
import com.example.plhistory.entities.Teams;

import java.util.List;

public interface TeamsService {

    List<Teams> getTeams();

    Teams getTeamByName(String name);

    String getStadiumByTeamName(String name);

    Integer getStadiumCapacityByTeamName(String name);

    String getCityByTeamName(String name);

    List<Matches> getLastFiveHomeMatchesByTeamName(String name);

    List<Matches> getLastFiveAwayMatchesByTeamName(String name);

    List<Matches> getLastFiveMatchesByTeamName(String name);

}
