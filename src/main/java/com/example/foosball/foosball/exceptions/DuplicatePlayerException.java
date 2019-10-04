package com.example.foosball.foosball.exceptions;

public class DuplicatePlayerInTeamException extends FoosballException {

    private static final long serialVersionUID = -6632221991763896266L;

    public DuplicatePlayerInTeamException() {
        super(Error.DUPLICATE_PLAYER);
    }
}
