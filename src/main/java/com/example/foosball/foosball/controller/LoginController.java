package com.example.foosball.foosball.controller;

import com.example.foosball.foosball.controller.dto.LoginRequest;
import com.example.foosball.foosball.service.FoosballService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("foosball")
public class LoginController {

    @Autowired
    private FoosballService foosballService;

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<HttpStatus> loginPlayer(@RequestBody final LoginRequest loginRequest) {
        this.foosballService.login(loginRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
