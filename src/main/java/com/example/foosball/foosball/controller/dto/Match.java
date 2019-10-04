package com.example.foosball.foosball.controller.dto;

import com.example.foosball.foosball.util.Court;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Match {
    private Team home;
    private Team away;

    @Setter
    private boolean isRunning = false;

    @Setter
    private boolean isPlayable = false;

    @Setter
    private boolean isFinished = false;

    public void addPlayer(final String playerId, final Court court) {

        if (Court.HOME.equals(court)) {
            if (null == this.home) {
                this.home = new Team();
            }

            this.home.addPlayer(playerId);

        } else {
            if (null == this.away) {
                this.away = new Team();
            }

            this.away.addPlayer(playerId);
        }

        log.info("Player {} entered the game for team {}.", playerId, court);


        if (this.home != null && this.away != null) {
            // game is ready to play

            this.setPlayable(true);
            log.info("Match is ready to play!");
        }
    }
}
