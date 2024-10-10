package com.first.flash.climbing.solution.exception;

import com.first.flash.climbing.solution.exception.exceptions.SolutionAccessDeniedException;
import com.first.flash.climbing.solution.exception.exceptions.SolutionCommentAccessDeniedException;
import com.first.flash.climbing.solution.exception.exceptions.SolutionCommentNotFoundException;
import com.first.flash.climbing.solution.exception.exceptions.SolutionNotFoundException;
import com.first.flash.global.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SolutionExceptionHandler {

    @ExceptionHandler(SolutionNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleSolutionNotFoundException(
        final SolutionNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(SolutionAccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleSolutionAccessDeniedException(
        final SolutionAccessDeniedException exception) {
        return getResponseWithStatus(HttpStatus.FORBIDDEN, exception);
    }

    @ExceptionHandler(SolutionCommentAccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleSolutionCommentAccessDeniedException(
        final SolutionCommentAccessDeniedException exception) {
        return getResponseWithStatus(HttpStatus.FORBIDDEN, exception);
    }

    @ExceptionHandler(SolutionCommentNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleSolutionCommentNotFoundException(
        final SolutionCommentNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    private ResponseEntity<ErrorResponseDto> getResponseWithStatus(final HttpStatus httpStatus,
        final RuntimeException exception) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(exception.getMessage());
        return ResponseEntity.status(httpStatus)
                             .body(errorResponse);
    }
}
