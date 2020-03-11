package com.example.foosball.foosball.service;

import com.example.foosball.foosball.controller.dto.MatchHistory;
import com.example.foosball.foosball.controller.dto.LoginRequest;
import com.example.foosball.foosball.controller.dto.Match;
import com.example.foosball.foosball.util.Court;

public interface FoosballService {
    void login(LoginRequest loginRequest);
    void logout(String playerId, String foosballTableId);
    void startMatch(String foosballTableId);
    Match scoreGoal(String foosballTableId, Court court);
    Match getMatch(String foosballTableId);
    void endGame(String foosballTableId);
    MatchHistory getMatchHistory();
}
