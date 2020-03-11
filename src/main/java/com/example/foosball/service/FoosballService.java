package com.example.foosball.service;

import com.example.foosball.controller.dto.LoginRequest;
import com.example.foosball.controller.dto.LogoutRequest;
import com.example.foosball.controller.dto.Match;
import com.example.foosball.controller.dto.MatchHistory;
import com.example.foosball.util.Court;

public interface FoosballService {
    void login(LoginRequest loginRequest);
    void logout(LogoutRequest logoutRequest);
    void startMatch(String foosballTableId);
    void scoreGoal(String foosballTableId, Court court);
    Match getMatch(String foosballTableId);
    void endGame(String foosballTableId);
    MatchHistory getMatchHistory();
}
