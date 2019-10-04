package com.example.foosball.foosball.exceptions;

public class GameRunningException extends FoosballException {
    private static final long serialVersionUID = -1091548581293752688L;

    public GameRunningException() {
        super(Error.GAME_IS_RUNNING);
    }
}
