package com.climbmetrics.backend.exception;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException() {
        super("No such user exists");
    }
}
