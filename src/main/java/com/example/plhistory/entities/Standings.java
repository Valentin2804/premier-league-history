package com.example.plhistory.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Standings {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Teams team;

    private Integer matchesPlayed;

    private Integer goalsScored;

    private Integer goalsConceded;

    private Integer points;

    private Integer goalDifference;
}
