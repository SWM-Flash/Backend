package com.first.flash.climbing.gym.exception;

import com.first.flash.climbing.gym.exception.exceptions.ClimbingGymNotFoundException;
import com.first.flash.climbing.gym.exception.exceptions.DifficultyNotFoundException;
import com.first.flash.climbing.gym.exception.exceptions.NoSectorGymException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClimbingGymExceptionHandler {

    @ExceptionHandler(ClimbingGymNotFoundException.class)
    public ResponseEntity<String> handleClimbingGymNotFoundException(
        final ClimbingGymNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(DifficultyNotFoundException.class)
    public ResponseEntity<String> handleDifficultyNotFoundException(
        final DifficultyNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(NoSectorGymException.class)
    public ResponseEntity<String> handleNoSectorGymException(
        final NoSectorGymException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    private ResponseEntity<String> getResponseWithStatus(final HttpStatus httpStatus,
        final RuntimeException exception) {
        return ResponseEntity.status(httpStatus)
                             .body(exception.getMessage());
    }
}
