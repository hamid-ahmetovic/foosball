package com.example.foosball.foosball.controller.dto;

import com.example.foosball.foosball.exceptions.DuplicatePlayerException;
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
        ++this.score;
    }

    public void addPlayer(final String playerId) {
        if (this.players.contains(playerId)) {
            log.error("Player {} is already logged in to the team", playerId);
            throw new DuplicatePlayerException();
        }

        this.players.add(playerId);
    }

    public boolean removePlayer(final String playerId) {
        return this.players.remove(playerId);
    }
}
