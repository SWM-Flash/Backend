package com.first.flash.climbing.solution.exception;

import com.first.flash.climbing.solution.exception.exceptions.SolutionAccessDeniedException;
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
        ErrorResponseDto errorResponse = new ErrorResponseDto(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(errorResponse);
    }

    @ExceptionHandler(SolutionAccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleSolutionAccessDeniedException(
        final SolutionAccessDeniedException exception) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(errorResponse);
    }
}
