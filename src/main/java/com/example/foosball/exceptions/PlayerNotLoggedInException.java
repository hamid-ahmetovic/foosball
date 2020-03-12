package com.example.foosball.exceptions;

public class PlayerNotLoggedInException extends FoosballException {

    private static final long serialVersionUID = -3439054153536482888L;

    public PlayerNotLoggedInException() {
        super(Error.PLAYER_NOT_LOGGED_IN);
    }
}
