package com.example.foosball.foosball.service;

import com.example.foosball.foosball.controller.dto.GameHistory;
import com.example.foosball.foosball.controller.dto.Score;

public interface FoosballService {
    void setScore(final String team);
    Score getScore();
    GameHistory getGameHistory();
}
