package com.example.foosball.service;

import com.example.foosball.controller.dto.LoginRequest;
import com.example.foosball.controller.dto.LogoutRequest;
import com.example.foosball.controller.dto.Match;
import com.example.foosball.controller.dto.MatchHistory;
import com.example.foosball.exceptions.MatchAlreadyRunningException;
import com.example.foosball.exceptions.MatchNotExistingException;
import com.example.foosball.exceptions.MatchNotPlayableException;
import com.example.foosball.exceptions.MatchNotRunningException;
import com.example.foosball.exceptions.PlayerNotLoggedInException;
import com.example.foosball.util.Court;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class FoosballServiceImpl implements FoosballService {

    public static final long FIVE_MINUTES = 6000000;
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
            match.setLastActionAt(LocalDateTime.now());
        } else {
            // create a new game
            final Match match = new Match();
            log.info("Created new foosball match with ID {}", foosballTableId);
            match.addPlayer(playerId, court);
            match.setLastActionAt(LocalDateTime.now());
            matches.put(foosballTableId, match);
        }
    }

    @Override
    public void logout(final LogoutRequest logoutRequest) {
        final String foosballTableId = logoutRequest.getFoosballTableId();
        final Match match = getMatch(foosballTableId);
        final boolean isRemoved = match.removePlayer(logoutRequest.getPlayerId());

        if (!isRemoved) {
            throw new PlayerNotLoggedInException();
        }

        if (match.isDead() || match.hasBeenLeft()) {
            removeMatch(foosballTableId);
        } else {
            match.setLastActionAt(LocalDateTime.now());
        }
    }

    @Override
    public void startMatch(final String foosballTableId) {
        final Match match = getMatch(foosballTableId);

        if (match.isRunning()) {
            throw new MatchAlreadyRunningException();
        }

        if (!match.isPlayable()) {
            throw new MatchNotPlayableException();
        }

        match.startMatch();
        match.setLastActionAt(LocalDateTime.now());
    }

    @Override
    public void scoreGoal(final String foosballTableId, final Court court) {
        final Match match = matches.get(foosballTableId);

        if (null == match) {
            throw new MatchNotExistingException();
        }

        if (match.isPlayable() && match.isRunning() && !match.isFinished()) {
            match.scoreGoal(court);
            match.setLastActionAt(LocalDateTime.now());
        } else {
            log.error("Match with ID: {} is not running.", foosballTableId);
            throw new MatchNotRunningException();
        }
    }

    @Override
    public Match getMatch(final String foosballTableId) {

        final Match match = matches.get(foosballTableId);

        if (null == match) {
            throw new MatchNotExistingException();
        }

        return match;
    }

    @Override
    public void finishMatch(final String foosballTableId) {
        final Match match = matches.get(foosballTableId);

        match.setRunning(false);
        match.setFinished(true);

        log.info("Finished match with ID: {}", foosballTableId);
    }

    @Override
    public MatchHistory getMatchHistory() {
        return null;
    }

    private void removeMatch(final String foosballTableId) {
        log.info("Removing foosball match with ID {}", foosballTableId);
        matches.remove(foosballTableId);
    }

    @Scheduled(fixedDelay = FIVE_MINUTES)
    private void removeDeadMatches() {
        for (final Map.Entry<String, Match> entry : matches.entrySet()) {
            final String foosballTableId = entry.getKey();
            final Match match = entry.getValue();

            if (match.isDead()) {
                log.info("Removing dead match with ID {}", foosballTableId);
                matches.remove(foosballTableId);
            }
        }
    }

}
