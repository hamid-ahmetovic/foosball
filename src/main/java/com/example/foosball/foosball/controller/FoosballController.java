package com.example.foosball.foosball.controller;

import com.example.foosball.foosball.controller.dto.FoosballEvent;
import com.example.foosball.foosball.controller.dto.GameHistory;
import com.example.foosball.foosball.controller.dto.Score;
import com.example.foosball.foosball.service.FoosballService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/foosball/score")
public class FoosballController {

    @Autowired
    private FoosballService foosballService;

    @PostMapping
    public ResponseEntity<HttpStatus> saveScore(@RequestBody @Valid FoosballEvent foosballEvent) {
        foosballService.setScore(foosballEvent.getTeam());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Score> getScore() {
        return ResponseEntity.ok(foosballService.getScore());
    }

    @GetMapping("/history")
    public ResponseEntity<GameHistory> getGameHistory() {
        return ResponseEntity.ok(foosballService.getGameHistory());
    }
}
