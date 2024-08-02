package com.first.flash.climbing.problem.exception;

import com.first.flash.climbing.problem.exception.exceptions.InvalidCursorException;
import com.first.flash.climbing.problem.exception.exceptions.ProblemNotFoundException;
import com.first.flash.climbing.problem.exception.exceptions.QueryProblemExpiredException;
import com.first.flash.climbing.problem.exception.exceptions.QueryProblemNotFoundException;
import com.first.flash.global.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProblemExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleProblemExpiredException(
        final QueryProblemExpiredException exception) {
        return getResponseWithStatus(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleQueryProblemNotFoundException(
        final QueryProblemNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleProblemNotFoundException(
        final ProblemNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleInvalidCursorException(
        final InvalidCursorException exception) {
        return getResponseWithStatus(HttpStatus.BAD_REQUEST, exception);
    }

    private ResponseEntity<ErrorResponseDto> getResponseWithStatus(final HttpStatus httpStatus,
        final RuntimeException exception) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(exception.getMessage());
        return ResponseEntity.status(httpStatus)
                             .body(errorResponse);
    }
}
