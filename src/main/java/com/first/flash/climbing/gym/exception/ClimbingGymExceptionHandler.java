package com.first.flash.climbing.gym.exception;

import com.first.flash.climbing.gym.exception.exceptions.ClimbingGymNotFoundException;
import com.first.flash.climbing.gym.exception.exceptions.DifficultyNotFoundException;
import com.first.flash.climbing.gym.exception.exceptions.DuplicateDifficultyLevelException;
import com.first.flash.climbing.gym.exception.exceptions.DuplicateDifficultyNameException;
import com.first.flash.climbing.gym.exception.exceptions.NoSectorGymException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
        final MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(errors);
    }

    @ExceptionHandler(DuplicateDifficultyLevelException.class)
    public ResponseEntity<String> handleDuplicateDifficultyLevelException(
        final DuplicateDifficultyLevelException exception) {
        return getResponseWithStatus(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(DuplicateDifficultyNameException.class)
    public ResponseEntity<String> handleDuplicateDifficultyNameException(
        final DuplicateDifficultyNameException exception) {
        return getResponseWithStatus(HttpStatus.BAD_REQUEST, exception);
    }

    private ResponseEntity<String> getResponseWithStatus(final HttpStatus httpStatus,
        final RuntimeException exception) {
        return ResponseEntity.status(httpStatus)
                             .body(exception.getMessage());
    }
}
