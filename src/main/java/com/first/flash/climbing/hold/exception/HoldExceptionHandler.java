package com.first.flash.climbing.hold.exception;

import com.first.flash.climbing.hold.exception.exceptions.HoldNotFoundException;
import com.first.flash.global.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HoldExceptionHandler {

    @ExceptionHandler(HoldNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleHoldNotFoundException(
        final HoldNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    private ResponseEntity<ErrorResponseDto> getResponseWithStatus(final HttpStatus httpStatus,
        final RuntimeException exception) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(exception.getMessage());
        return ResponseEntity.status(httpStatus)
                             .body(errorResponse);
    }
}
