package com.example.foosball.foosball.controller;

import com.example.foosball.foosball.controller.dto.FoosballEvent;
import com.example.foosball.foosball.controller.dto.Match;
import com.example.foosball.foosball.service.FoosballService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<Match> saveScore(@RequestBody @Valid final FoosballEvent foosballEvent) {
        final Match match = this.foosballService.scoreGoal(foosballEvent.getFoosballTable(), foosballEvent.getTeam());
        return ResponseEntity.ok(match);
    }


    @GetMapping("/score/{foosballTableId}")
    public ResponseEntity<Match> getScore(@PathVariable final String foosballTableId ) {
        return ResponseEntity.ok(this.foosballService.getScore(foosballTableId));
    }

    @GetMapping("/reset")
    public ResponseEntity<HttpStatus> resetGame() {
        //this.foosballService.endGame();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler({ Throwable.class })
    public void handleException(final Throwable ex) {
        FoosballController.log.error(ex.getMessage());
    }
}
