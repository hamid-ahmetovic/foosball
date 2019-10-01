package com.example.foosball.foosball.service;

import com.example.foosball.foosball.controller.dto.GameHistory;
import com.example.foosball.foosball.controller.dto.Score;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FoosballServiceImpl implements FoosballService {

    private final static String HOME = "home";

    private int homeScore = 0;
    private int awayScore = 0;
    private List<LocalTime> homeScoreHistory = new ArrayList<>();
    private List<LocalTime> awayScoreHistory = new ArrayList<>();
    private GameHistory gameHistory;


    @PostConstruct
    public void setGameHistory() {
        gameHistory = new GameHistory();
        gameHistory.setScores(new ArrayList<>());
    }

    @Override
    public void setScore(final String team) {
        final LocalTime now = LocalTime.now();
        if (HOME.equalsIgnoreCase(team)) {
            homeScore++;
            homeScoreHistory.add(now);
        } else {
            awayScore++;
            awayScoreHistory.add(now);
        }


    }

    @Override
    public Score getScore() {
        final Score score = new Score();

        score.setHomeScore(this.homeScore);
        score.setAwayScore(this.awayScore);
        score.setHomeScoreHistory(this.homeScoreHistory);
        score.setAwayScoreHistory(this.awayScoreHistory);

        return score;
    }

    @Override
    public GameHistory getGameHistory() {
        return null;
    }
}
