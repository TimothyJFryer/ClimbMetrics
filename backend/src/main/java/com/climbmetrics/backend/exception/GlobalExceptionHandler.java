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

    @ExceptionHandler(NoSuchClimbException.class)
    public ResponseEntity<ApiError> handleNonExistantClimb(
            NoSuchClimbException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        "NO_CLIMB",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<ApiError> handleUnauthorizedUser(
            UnauthorizedUserException ex) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiError(
                        "UNAUTHORIZED",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ApiError> handleStorageException(
            StorageException ex) {

        return ResponseEntity
                .status(HttpStatus.INSUFFICIENT_STORAGE)
                .body(new ApiError(
                        "UNAUTHORIZED",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<ApiError> handleStorageFileNotFound(
            StorageFileNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        "NOT_FOUND",
                        ex.getMessage()
                ));
    }
}