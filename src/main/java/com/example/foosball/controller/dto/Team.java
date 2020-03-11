package com.example.foosball.controller.dto;

import com.example.foosball.exceptions.DuplicatePlayerException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Getter
@Slf4j
public class Team {

    private int score = 0;

    private final Set<String> players = new HashSet<>();

    public void scoreGoal() {
        ++score;
    }
    public void resetScore() { score = 0; }

    /*package*/ void addPlayer(final String playerId) {
        if (players.contains(playerId)) {
            log.error("Player {} is already logged in to the team", playerId);
            throw new DuplicatePlayerException();
        }

        players.add(playerId);
    }

    /*package*/ boolean removePlayer(final String playerId) {
        return players.remove(playerId);
    }

    /*package*/ boolean isReadyToPlay() {
        return players.size() > 0;
    }
}
