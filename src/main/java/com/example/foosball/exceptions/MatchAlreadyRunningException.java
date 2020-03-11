package com.example.foosball.exceptions;

public class MatchAlreadyRunningException extends FoosballException {
    private static final long serialVersionUID = -1091548581293752688L;

    public MatchAlreadyRunningException() {
        super(Error.MATCH_IS_ALREADY_RUNNING);
    }
}
