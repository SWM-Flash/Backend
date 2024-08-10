package com.first.flash.climbing.sector.exception;

import com.first.flash.climbing.sector.exception.exceptions.InvalidRemovalDateException;
import com.first.flash.climbing.sector.exception.exceptions.SectorNotFoundException;
import com.first.flash.global.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SectorExceptionHandler {

    @ExceptionHandler(SectorNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleSectorNotFoundException(
        final SectorNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(InvalidRemovalDateException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidRemovalDateException(
        final InvalidRemovalDateException exception) {
        return getResponseWithStatus(HttpStatus.BAD_REQUEST, exception);
    }

    private ResponseEntity<ErrorResponseDto> getResponseWithStatus(final HttpStatus httpStatus,
        final RuntimeException exception) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(exception.getMessage());
        return ResponseEntity.status(httpStatus)
                             .body(errorResponse);
    }
}
