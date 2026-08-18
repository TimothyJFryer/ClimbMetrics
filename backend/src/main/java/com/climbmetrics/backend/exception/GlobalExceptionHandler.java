package com.climbmetrics.backend.exception;

import com.climbmetrics.backend.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(
            UserAlreadyExistsException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        "USER_ALREADY_EXISTS",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ApiError> handleIncorrectPassword(
            IncorrectPasswordException ex) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiError(
                        "INCORRECT_PASSWORD",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<ApiError> handleNonExistantUser(
            NoSuchUserException ex) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError(
                        "NO_USER",
                        ex.getMessage()
                ));
    }

}
