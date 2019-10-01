package com.example.foosball.foosball.controller;

import com.example.foosball.foosball.controller.dto.FoosballEvent;
import com.example.foosball.foosball.controller.dto.GameHistory;
import com.example.foosball.foosball.controller.dto.Score;
import com.example.foosball.foosball.service.FoosballService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("foosball")
@Slf4j
public class FoosballController {

    @Autowired
    private FoosballService foosballService;

    @PostMapping("/score")
    public ResponseEntity<HttpStatus> saveScore(@RequestBody @Valid FoosballEvent foosballEvent) {
        foosballService.setScore(foosballEvent.getTeam());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/score")
    public ResponseEntity<Score> getScore() {
        return ResponseEntity.ok(foosballService.getScore());
    }

    @GetMapping("/history")
    public ResponseEntity<GameHistory> getGameHistory() {
        return null;
    }

    @GetMapping("/reset")
    public ResponseEntity<HttpStatus> resetGame() {
        foosballService.resetGame();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler({ Throwable.class })
    public void handleException(Throwable ex) {
        log.error(ex.getMessage());
    }
}
