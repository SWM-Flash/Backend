package com.first.flash.climbing.gym.exception;

import com.first.flash.climbing.gym.exception.exceptions.ClimbingGymNotFoundException;
import com.first.flash.climbing.gym.exception.exceptions.DifficultyNotFoundException;
import com.first.flash.climbing.gym.exception.exceptions.DuplicateDifficultyLevelException;
import com.first.flash.climbing.gym.exception.exceptions.DuplicateDifficultyNameException;
import com.first.flash.climbing.gym.exception.exceptions.NoSectorGymException;
import com.first.flash.global.dto.ErrorResponseDto;
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
    public ResponseEntity<ErrorResponseDto> handleClimbingGymNotFoundException(
        final ClimbingGymNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(DifficultyNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleDifficultyNotFoundException(
        final DifficultyNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(NoSectorGymException.class)
    public ResponseEntity<ErrorResponseDto> handleNoSectorGymException(
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
    public ResponseEntity<ErrorResponseDto> handleDuplicateDifficultyLevelException(
        final DuplicateDifficultyLevelException exception) {
        return getResponseWithStatus(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(DuplicateDifficultyNameException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateDifficultyNameException(
        final DuplicateDifficultyNameException exception) {
        return getResponseWithStatus(HttpStatus.BAD_REQUEST, exception);
    }

    private ResponseEntity<ErrorResponseDto> getResponseWithStatus(final HttpStatus httpStatus,
        final RuntimeException exception) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(exception.getMessage());
        return ResponseEntity.status(httpStatus)
                             .body(errorResponse);
    }
}
