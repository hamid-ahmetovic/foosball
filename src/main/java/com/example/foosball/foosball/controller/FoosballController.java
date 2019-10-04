package com.example.foosball.foosball.controller;

import com.example.foosball.foosball.controller.dto.FoosballEvent;
import com.example.foosball.foosball.controller.dto.Match;
import com.example.foosball.foosball.service.FoosballService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("foosball")
@Slf4j
public class FoosballController {

    private final FoosballService foosballService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public FoosballController(final FoosballService foosballService, final SimpMessagingTemplate simpMessagingTemplate) {
        this.foosballService = foosballService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PutMapping("/match/start/{foosballTableId}")
    public ResponseEntity<HttpStatus> startMatch(@PathVariable final String foosballTableId) {
        this.foosballService.startMatch(foosballTableId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/match/score")
    public void scoreGoal(@Valid @RequestBody final FoosballEvent foosballEvent) {
        final Match match = this.foosballService.scoreGoal(foosballEvent.getFoosballTableId(), foosballEvent.getCourt());
        this.simpMessagingTemplate.convertAndSend("/match/" + foosballEvent.getFoosballTableId(), match);
    }

    @PutMapping("/match/reset/{foosballTableId}")
    public ResponseEntity<HttpStatus> resetMatch(@PathVariable final String foosballTableId) {
        this.foosballService.endGame(foosballTableId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler({ Throwable.class })
    public void handleException(final Throwable ex) {
        log.error(ex.getMessage());
    }
}
