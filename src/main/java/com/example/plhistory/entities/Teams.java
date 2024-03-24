package com.example.plhistory.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Teams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer yearOfCreating;

    private String stadium;

    private Integer stadiumCapacity;

    private String city;

    private String website;
}

