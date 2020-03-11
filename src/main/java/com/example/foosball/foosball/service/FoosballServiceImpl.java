package com.example.foosball.foosball.service;

import com.example.foosball.foosball.controller.dto.MatchHistory;
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

    private MatchHistory matchHistory;
    private Map<String, Match> matches;

    @PostConstruct
    public void init() {
        // create thread safe matches
        this.matches = Collections.synchronizedMap(new HashMap<>());

        this.matchHistory = new MatchHistory();
        this.matchHistory.setMatches(new ArrayList<>());
    }

    @Override
    public void login(final LoginRequest loginRequest) {

        final String playerId = loginRequest.getPlayerId();
        final String foosballTableId = loginRequest.getFoosballTableId();
        final Court court = loginRequest.getCourt();

        if (this.matches.containsKey(foosballTableId)) {
            // game is existing

            log.info("Found running match with ID {}", foosballTableId);

            final Match match = this.matches.get(foosballTableId);

            if (match.isRunning()) {
                // cannot login to a running game
                log.info("User {} tried to log in the a running match {}.", playerId, foosballTableId);
                throw new GameRunningException();
            }

            if (match.isFinished()) {
                // create new game if match has finished
                this.matches.put(foosballTableId, new Match());

                log.info("Started a new foosball match with ID {}", foosballTableId);
            }

            this.matches.get(foosballTableId).addPlayer(playerId, court);
        } else {
            // create a new game

            final Match match = new Match();

            log.info("Created new foosball match with ID {}", foosballTableId);

            match.addPlayer(playerId, court);

            this.matches.put(foosballTableId, match);
        }
    }

    @Override
    public void logout(final String playerId, final String foosballTableId) {

    }

    @Override
    public void startMatch(final String foosballTableId) {
        final Match match = this.matches.get(foosballTableId);

        if (match.isPlayable()) {
            match.setRunning(true);
            log.info("Match with ID {} has started!", foosballTableId);
        } else {
            log.error("Match with ID {} is not ready yet!", foosballTableId);
        }
    }

    @Override
    public Match scoreGoal(final String foosballTableId, final Court court) {
        final Match match = this.matches.get(foosballTableId);

        if (match.isPlayable() && match.isRunning() && !match.isFinished()) {
            // scoring goals is only possible for playable matches

            final Team team = Court.HOME.equals(court) ? match.getHome() : match.getAway();

            team.scoreGoal();
            log.info("Team {} scored a goal! Match ID: {}", court, foosballTableId);

            // TODO: Make top score configurable
            if (team.getScore() > 9) {
                // TODO: save final match status to DB
                match.setPlayable(false);
                match.setRunning(false);
                match.setFinished(true);

                log.info("Match {} has finished. HOME: {} - AWAY: {}",
                         foosballTableId,
                         match.getHome().getScore() ,
                         match.getAway().getScore());
            }
        }

        return match;
    }

    @Override
    public Match getMatch(final String foosballTableId) {
        return this.matches.get(foosballTableId);
    }

    @Override
    public void endGame(final String foosballTableId) {
        final Match match = this.matches.get(foosballTableId);

        match.setRunning(false);
        match.setFinished(true);

        log.info("Finished match with ID: {}", foosballTableId);
    }

    @Override
    public MatchHistory getMatchHistory() {
        return null;
    }

}
