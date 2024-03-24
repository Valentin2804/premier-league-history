package com.example.plhistory.entities;

import jakarta.persistence.*;
import lombok.Data;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Entity
@Data
public class ManagerAppointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate hireDate;

    @Nullable
    private LocalDate departureDate;

    @ManyToOne
    private Teams team;

    @ManyToOne
    private Managers manager;
}
