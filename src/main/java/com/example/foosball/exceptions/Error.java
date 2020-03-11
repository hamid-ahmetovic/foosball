package com.example.foosball.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Error {
    MATCH_IS_ALREADY_RUNNING(1000, "Match is already running."),
    MATCH_IS_NOT_RUNNING(1010, "Match is not running."),
    MATCH_IS_NOT_EXISTING(1020, "Match is not existing"),
    DUPLICATE_PLAYER(2000, "This player is already logged in.");

    private final int code;
    private final String description;
}
