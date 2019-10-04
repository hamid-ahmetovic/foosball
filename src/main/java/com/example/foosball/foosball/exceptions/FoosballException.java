package com.example.foosball.foosball.exceptions;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public abstract class FoosballException extends RuntimeException {

    private final Error error;

    private static final long serialVersionUID = -4434241111113972732L;

    protected FoosballException(@NotNull final Error error) {
        super(error.getDescription());
        this.error = error;
    }
}
