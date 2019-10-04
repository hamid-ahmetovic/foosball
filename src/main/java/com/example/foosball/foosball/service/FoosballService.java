package com.example.foosball.foosball.service;

import com.example.foosball.foosball.controller.dto.GameHistory;
import com.example.foosball.foosball.controller.dto.LoginRequest;
import com.example.foosball.foosball.controller.dto.Match;


public interface FoosballService {
    Match scoreGoal(String foosballTableId, String team);
    Match getScore(String foosballTableId);
    GameHistory getGameHistory();
    void endGame(String foosballTableId);
    void login(LoginRequest loginRequest);
}
