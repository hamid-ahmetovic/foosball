package com.example.foosball.service;

import com.example.foosball.controller.dto.LoginRequest;
import com.example.foosball.controller.dto.LogoutRequest;
import com.example.foosball.controller.dto.Match;
import com.example.foosball.controller.dto.MatchHistory;
import com.example.foosball.controller.dto.Team;
import com.example.foosball.exceptions.MatchAlreadyRunningException;
import com.example.foosball.exceptions.MatchNotExistingException;
import com.example.foosball.exceptions.MatchNotRunningException;
import com.example.foosball.util.Court;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class FoosballServiceImpl implements FoosballService {

    private MatchHistory matchHistory;
    private Map<String, Match> matches;

    @PostConstruct
    public void init() {
        // create thread safe matches
        matches = Collections.synchronizedMap(new HashMap<>());

        matchHistory = new MatchHistory();
        matchHistory.setMatches(new ArrayList<>());
    }

    @Override
    public void login(final LoginRequest loginRequest) {

        final String playerId = loginRequest.getPlayerId();
        final String foosballTableId = loginRequest.getFoosballTableId();
        final Court court = loginRequest.getCourt();

        if (matches.containsKey(foosballTableId)) {
            // game is existing

            log.info("Found running match with ID {}", foosballTableId);

            final Match match = matches.get(foosballTableId);

            if (match.isRunning()) {
                // cannot login to a running game
                log.info("User {} tried to log in the a running match {}.", playerId, foosballTableId);
                throw new MatchAlreadyRunningException();
            }

            if (match.isFinished()) {
                // create new game if match has finished
                matches.put(foosballTableId, new Match());
                log.info("Started a new foosball match with ID {}", foosballTableId);
            }

            matches.get(foosballTableId).addPlayer(playerId, court);
        } else {
            // create a new game
            final Match match = new Match();
            log.info("Created new foosball match with ID {}", foosballTableId);
            match.addPlayer(playerId, court);
            matches.put(foosballTableId, match);
        }
    }

    @Override
    public void logout(final LogoutRequest logoutRequest) {
        Optional.ofNullable(matches.get(logoutRequest.getFoosballTableId()))
                .ifPresent(match -> match.removePlayer(logoutRequest.getPlayerId()));

    }

    @Override
    public void startMatch(final String foosballTableId) {
        final Match match = matches.get(foosballTableId);
        if (null != match) {
            if (match.isRunning()) {
                throw new MatchAlreadyRunningException();
            }

            match.startMatch();
        } else {
            throw new MatchNotExistingException();
        }

    }

    @Override
    public void scoreGoal(final String foosballTableId, final Court court) {
        final Match match = matches.get(foosballTableId);

        if (null == match) {
            throw new MatchNotExistingException();
        }

        if (match.isPlayable() && match.isRunning() && !match.isFinished()) {
            // scoring goals is only possible for playable matches

            final Team team = Court.HOME.equals(court) ? match.getHOME() : match.getAWAY();

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
                         match.getHOME().getScore() ,
                         match.getAWAY().getScore());
            }
        } else {
            log.error("Match with ID: {} is not running.", foosballTableId);
            throw new MatchNotRunningException();
        }
    }

    @Override
    public Match getMatch(final String foosballTableId) {
        return matches.get(foosballTableId);
    }

    @Override
    public void endGame(final String foosballTableId) {
        final Match match = matches.get(foosballTableId);

        match.setRunning(false);
        match.setFinished(true);

        log.info("Finished match with ID: {}", foosballTableId);
    }

    @Override
    public MatchHistory getMatchHistory() {
        return null;
    }

}
