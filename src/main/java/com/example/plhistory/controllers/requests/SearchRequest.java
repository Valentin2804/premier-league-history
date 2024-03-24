package com.example.plhistory.controllers.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
    private String homeTeam;
    private String awayTeam;
}
