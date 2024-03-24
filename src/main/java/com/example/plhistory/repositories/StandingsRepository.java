package com.example.plhistory.repositories;

import com.example.plhistory.entities.Standings;
import com.example.plhistory.entities.Teams;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandingsRepository extends JpaRepository<Standings, Integer> {
    Standings findByTeam(Teams team);

    List<Standings> findAll(Sort sort);
}
