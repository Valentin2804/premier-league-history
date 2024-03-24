package com.example.plhistory.repositories;

import com.example.plhistory.entities.PremierLeague;
import com.example.plhistory.entities.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremierLeagueRepository extends JpaRepository<PremierLeague, Long> {

    Integer countAllByWinner(Teams team);
}
