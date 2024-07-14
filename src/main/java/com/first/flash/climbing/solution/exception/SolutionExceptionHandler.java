package com.first.flash.climbing.solution.exception;

import com.first.flash.climbing.solution.exception.exceptions.SolutionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SolutionExceptionHandler {

    @ExceptionHandler(SolutionNotFoundException.class)
    public ResponseEntity<String> handleSolutionNotFoundException(
        final SolutionNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(exception.getMessage());
    }
}
