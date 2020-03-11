package com.example.foosball.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class MatchHistory {
    private List<Match> matches;
}
