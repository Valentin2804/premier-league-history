package com.example.plhistory.repositories;

import com.example.plhistory.entities.Matches;
import com.example.plhistory.entities.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatchesRepository extends JpaRepository<Matches, Long> {

    List<Matches> findTop5ByHomeTeamOrderByDateDesc(Teams homeTeam);

    List<Matches> findTop5ByAwayTeamOrderByDateDesc(Teams awayTeam);

    List<Matches> findTop5ByHomeTeamOrAwayTeamOrderByDateDesc(Teams homeTeam, Teams awayTeam);

    List<Matches> findByDateBetweenAndAwayTeamOrDateBetweenAndHomeTeam
            (LocalDate startSeason, LocalDate endSeason1, Teams homeTeam,
             LocalDate startSeason1, LocalDate endSeason, Teams awayTeam);
}
