package com.example.foosball.exceptions;

public class MatchNotPlayableException extends FoosballException {

    private static final long serialVersionUID = -4352490062781135842L;

    public MatchNotPlayableException() {
        super(Error.MATCH_IS_NOT_PLAYABLE);
    }
}
