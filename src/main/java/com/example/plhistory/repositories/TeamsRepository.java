package com.example.plhistory.repositories;

import com.example.plhistory.entities.Matches;
import com.example.plhistory.entities.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamsRepository extends JpaRepository<Teams, Long> {

    Teams getTeamsByName(String name);
}
