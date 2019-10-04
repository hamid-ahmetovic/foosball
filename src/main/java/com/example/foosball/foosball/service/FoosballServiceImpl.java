package com.example.foosball.foosball.service;

import com.example.foosball.foosball.controller.dto.GameHistory;
import com.example.foosball.foosball.controller.dto.LoginRequest;
import com.example.foosball.foosball.controller.dto.Match;
import com.example.foosball.foosball.controller.dto.Team;
import com.example.foosball.foosball.exceptions.GameRunningException;
import com.example.foosball.foosball.util.Court;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class FoosballServiceImpl implements FoosballService {

    private static final String HOME = "HOME";
    private GameHistory gameHistory;
    private Map<String, Match> games;

    @PostConstruct
    public void init() {
        // create thread safe games
        this.games = Collections.synchronizedMap(new HashMap<>());

        this.gameHistory = new GameHistory();
        this.gameHistory.setMatches(new ArrayList<>());
    }

    @Override
    public Match scoreGoal(final String foosballTableId, final String teamId) {
        final Match match = this.games.get(foosballTableId);

        final Team team;

        if (FoosballServiceImpl.HOME.equals(teamId)) {
            team = match.getHome();
        } else {
            team = match.getAway();
        }

        match.getHome().scoreGoal();

        // TODO: Make top score configurable
        if (team.getScore() > 9) {
            // TODO: save final match status to DB
            match.setRunning(false);
            this.games.remove(foosballTableId);
        }

        return match;
    }

    @Override
    public Match getScore(final String foosballTableId) {
        return this.games.get(foosballTableId);
    }

    @Override
    public GameHistory getGameHistory() {
        return null;
    }

    @Override
    public void endGame(final String foosballTableId) {
        this.games.remove(foosballTableId);
    }

    @Override
    public void login(final LoginRequest loginRequest) {

        final String playerId = loginRequest.getPlayerId();
        final String foosballTableId = loginRequest.getFoosballTableId();
        final Court court = loginRequest.getCourt();

        if (this.games.containsKey(foosballTableId)) {
            // game is existing

            log.info("Found running game with id {}", foosballTableId);

            final Match match = this.games.get(foosballTableId);

            if (match.isRunning()) {
                // cannot login to a running game
                log.info("User {} tried to log in to a running game.", playerId);
                throw new GameRunningException();
            }

            match.addPlayer(playerId, court);
        } else {
            // create a new game

            final Match match = new Match();

            log.info("Created new foosball match with id {}", foosballTableId);

            match.addPlayer(playerId, court);

            this.games.put(foosballTableId, match);
        }
    }
}
