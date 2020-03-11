package com.example.foosball.controller.dto;

import com.example.foosball.util.Court;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Match {
    private final Team HOME = new Team();
    private final Team AWAY = new Team();

    @Setter
    private boolean isRunning = false;

    @Setter
    private boolean isPlayable = false;

    @Setter
    private boolean isFinished = false;

    public void addPlayer(final String playerId, final Court court) {
        if (Court.HOME.equals(court)) {
            HOME.addPlayer(playerId);
        } else {
            AWAY.addPlayer(playerId);
        }

        log.info("Player {} entered the game for team {}.", playerId, court);

        checkMatchStatus();
    }

    public void removePlayer(final String playerId) {
        final boolean isRemoved = HOME.removePlayer(playerId) | AWAY.removePlayer(playerId);

        if (isRemoved) {
            log.info("Player {} left the game.", playerId);
            checkMatchStatus();
        }
    }

    public void startMatch() {
        if (isPlayable || isFinished) {
            HOME.resetScore();
            AWAY.resetScore();

            isRunning = true;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void checkMatchStatus() {
        if (HOME.isReadyToPlay() && AWAY.isReadyToPlay()) {
            setPlayable(true);
            log.info("Match is ready to play!");
        } else {
            setPlayable(false);
            setRunning(false);
            log.info("Match is not ready to play!");
        }
    }
}
