package com.example.plhistory.repositories;

import com.example.plhistory.entities.Managers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagersRepository extends JpaRepository<Managers, Long> {

    Managers findByName(String name);

    @Query("SELECT DISTINCT e.nation FROM Managers e")
    List<String> findDistinctNation();
}
