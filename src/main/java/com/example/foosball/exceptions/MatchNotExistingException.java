package com.example.foosball.exceptions;

public class MatchNotExistingException extends FoosballException {
    private static final long serialVersionUID = -1091548581293752688L;

    public MatchNotExistingException() {
        super(Error.MATCH_IS_NOT_EXISTING);
    }
}
