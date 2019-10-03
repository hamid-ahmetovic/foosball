package com.example.foosball.foosball.controller.dto;

import com.example.foosball.foosball.util.Court;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
public class MatchStatus {
    private Team home;
    private Team away;

    @Setter
    private boolean isMatchRunning = true;

    @Setter
    private boolean isMatchStarted = false;

    public void addTeam(final Team team) {
        if (Court.HOME.equals(team.getCourt())) {
            this.home = team;
        } else {
            this.away = team;
        }
    }
}
