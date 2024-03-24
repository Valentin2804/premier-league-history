package com.example.plhistory.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PremierLeague {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String season;

    @ManyToOne
    private Teams winner;

    @ManyToOne
    private Teams runnerUp;

    private Integer winnerPoints;

    private Integer runnerUpPoints;
}
