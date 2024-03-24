package com.example.plhistory.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Data
public class Matches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    private Teams homeTeam;

    @ManyToOne
    private Teams awayTeam;

    private Integer homeTeamGoals;

    private Integer awayTeamGoals;

    public String getResult(){
        return homeTeamGoals.toString() + " : " + awayTeamGoals.toString();
    }

    public String getSeason(){
        int startYear;
        int endYear;

        if(date.getMonthValue() < 7){
            startYear = date.getYear() - 1;
            endYear = date.getYear();
        }else {
            startYear = date.getYear();
            endYear = date.getYear() + 1;
        }

        return startYear + "/" + String.valueOf(endYear).substring(2);
    }

    public String getDate(){

        String formattedDate = null;

        try {
            DateTimeFormatter desiredFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            formattedDate = date.format(desiredFormatter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    @Override
    public String toString(){
        return homeTeam.getName() + " "
                + getResult() + " "
                + awayTeam.getName() + " "
                + date.toString() + " Season: "
                + getSeason();
    }
}
