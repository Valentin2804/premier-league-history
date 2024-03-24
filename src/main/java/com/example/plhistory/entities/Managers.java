package com.example.plhistory.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;
import java.time.LocalDate;

@Entity
@Data
public class Managers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthday;

    private String nation;
}
