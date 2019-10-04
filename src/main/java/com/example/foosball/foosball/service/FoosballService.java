package com.example.foosball.foosball.service;

import com.example.foosball.foosball.controller.dto.MatchHistory;
import com.example.foosball.foosball.controller.dto.LoginRequest;
import com.example.foosball.foosball.controller.dto.Match;
import com.example.foosball.foosball.util.Court;

public interface FoosballService {
    void startMatch(String foosballTableId);
    Match scoreGoal(String foosballTableId, Court court);
    Match getMatch(String foosballTableId);
    MatchHistory getMatchHistory();
    void endGame(String foosballTableId);
    void login(LoginRequest loginRequest);
}
