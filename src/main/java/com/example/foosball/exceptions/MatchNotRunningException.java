package com.example.foosball.exceptions;

public class MatchNotRunningException extends FoosballException {
    private static final long serialVersionUID = -1091548581293752688L;

    public MatchNotRunningException() {
        super(Error.MATCH_IS_NOT_RUNNING);
    }
}
