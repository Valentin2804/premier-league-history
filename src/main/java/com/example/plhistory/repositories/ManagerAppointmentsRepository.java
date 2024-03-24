package com.example.plhistory.repositories;

import com.example.plhistory.entities.ManagerAppointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerAppointmentsRepository extends JpaRepository<ManagerAppointments, Long> {
}
