package com.example.foosball.foosball.controller.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class Score {

    private int homeScore;
    private int awayScore;
    private LocalTime playTime = LocalTime.now();
    private List<LocalTime> homeScoreHistory;
    private List<LocalTime> awayScoreHistory;
}
