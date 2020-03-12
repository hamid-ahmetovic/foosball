package com.example.foosball.controller.dto;

import com.example.foosball.util.Court;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Slf4j
public class Match {

    private final Team HOME = new Team();
    private final Team AWAY = new Team();

    @Setter
    private LocalDateTime lastActionAt;

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

    public boolean removePlayer(final String playerId) {
        final boolean isRemoved = HOME.removePlayer(playerId) | AWAY.removePlayer(playerId);

        if (isRemoved) {
            log.info("Player {} left the game.", playerId);
            checkMatchStatus();
        }

        return isRemoved;
    }

    public void startMatch() {
        HOME.resetScore();
        AWAY.resetScore();

        isRunning = true;
        isFinished = false;

    }

    public void scoreGoal(final Court court) {
        final Team team = Court.HOME.equals(court) ? HOME : AWAY;

        team.scoreGoal();

        log.info("Team {} scored a goal! Match ID: {}", court);

        // TODO: Make top score configurable
        if (team.getScore() > 9) {
            // TODO: save final match status to DB
            setRunning(false);
            setFinished(true);

            log.info("Final Score HOME: {} - AWAY: {}",
                     HOME.getScore() ,
                     AWAY.getScore());
        }
    }

    public boolean isEmpty() {
        return HOME.getPlayers().isEmpty() && AWAY.getPlayers().isEmpty();
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
