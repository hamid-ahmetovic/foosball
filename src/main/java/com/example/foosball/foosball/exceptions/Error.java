package com.example.foosball.foosball.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Error {
    GAME_IS_RUNNING(1000, "Game is already running."),
    DUPLICATE_PLAYER(2000, "This player is already logged in.");

    private final int code;
    private final String description;
}
