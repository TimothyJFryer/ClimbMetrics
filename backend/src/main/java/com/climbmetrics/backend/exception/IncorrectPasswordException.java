package com.climbmetrics.backend.exception;

public class IncorrectPasswordException extends RuntimeException {
  public IncorrectPasswordException(String message) {
    super(message);
  }
}
