package com.climbmetrics.backend.exception;

public class NoSuchClimbException extends RuntimeException {
    public NoSuchClimbException() {
        super("No such climb exist");
    }
}
