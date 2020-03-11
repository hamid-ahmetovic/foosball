package com.example.foosball.controller;

import com.example.foosball.controller.dto.LoginRequest;
import com.example.foosball.controller.dto.LogoutRequest;
import com.example.foosball.controller.dto.Match;
import com.example.foosball.controller.dto.ScoreEvent;
import com.example.foosball.exceptions.Error;
import com.example.foosball.exceptions.FoosballException;
import com.example.foosball.service.FoosballService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FoosballController {

    private final FoosballService foosballService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody @Valid final LoginRequest loginRequest) {
        foosballService.login(loginRequest);
        pushMatchScoreToWebSocket(loginRequest.getFoosballTableId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestBody @Valid final LogoutRequest logoutRequest) {
        foosballService.logout(logoutRequest);
        pushMatchScoreToWebSocket(logoutRequest.getFoosballTableId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/match/start/{foosballTableId}")
    public ResponseEntity<String> startMatch(@PathVariable final String foosballTableId) {
        foosballService.startMatch(foosballTableId);
        pushMatchScoreToWebSocket(foosballTableId);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/match/score")
    public ResponseEntity<String> scoreGoal(@RequestBody @Valid final ScoreEvent scoreEvent) {
        foosballService.scoreGoal(scoreEvent.getFoosballTableId(), scoreEvent.getCourt());
        pushMatchScoreToWebSocket(scoreEvent.getFoosballTableId());
        return ResponseEntity.ok("OK");
    }

    private void pushMatchScoreToWebSocket(final String foosballTableId) {
        final Match match = foosballService.getMatch(foosballTableId);
        if (null != match) {
            simpMessagingTemplate.convertAndSend("/match/" + foosballTableId, match);
        }
    }

    @ExceptionHandler({ FoosballException.class })
    public ResponseEntity<ErrorResponse> handleException(final FoosballException ex) {
        log.error(ex.getMessage());
        final Error error = ex.getError();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(error.getCode(), error.getDescription()));
    }

    @RequiredArgsConstructor
    @Getter
    private static class ErrorResponse {
        private final int errorCode;
        private final String errorMessage;
    }
}
