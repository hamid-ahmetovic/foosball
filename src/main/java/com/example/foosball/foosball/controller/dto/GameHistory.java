package com.example.foosball.foosball.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class GameHistory {
    private List<Match> matches;
}
