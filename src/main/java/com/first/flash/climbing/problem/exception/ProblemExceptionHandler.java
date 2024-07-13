package com.first.flash.climbing.problem.exception;

import com.first.flash.climbing.problem.exception.exceptions.QueryProblemExpiredException;
import com.first.flash.climbing.problem.exception.exceptions.QueryProblemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProblemExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleProblemExpiredException(
        final QueryProblemExpiredException exception) {
        return getResponseWithStatus(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleProblemNotFoundException(
        final QueryProblemNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    private ResponseEntity<String> getResponseWithStatus(final HttpStatus httpStatus,
        final RuntimeException exception) {
        return ResponseEntity.status(httpStatus)
                             .body(exception.getMessage());
    }
}
