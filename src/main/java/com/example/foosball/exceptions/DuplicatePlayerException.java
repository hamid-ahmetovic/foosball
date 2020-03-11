package com.example.foosball.exceptions;

public class DuplicatePlayerException extends FoosballException {

    private static final long serialVersionUID = -6632221991763896266L;

    public DuplicatePlayerException() {
        super(Error.DUPLICATE_PLAYER);
    }
}
