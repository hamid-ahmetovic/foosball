package com.example.foosball.foosball.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Court {
    HOME("HOME"),
    AWAY("AWAY");

    private final String courtId;
}
