package com.first.flash.climbing.sector.exception;

import com.first.flash.climbing.sector.exception.exceptions.InvalidRemovalDateException;
import com.first.flash.climbing.sector.exception.exceptions.SectorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SectorExceptionHandler {

    @ExceptionHandler(SectorNotFoundException.class)
    public ResponseEntity<String> handleSectorNotFoundException(
        final SectorNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(InvalidRemovalDateException.class)
    public ResponseEntity<String> handleInvalidRemovalDateException(
        final InvalidRemovalDateException exception) {
        return getResponseWithStatus(HttpStatus.BAD_REQUEST, exception);
    }

    private ResponseEntity<String> getResponseWithStatus(final HttpStatus httpStatus,
        final RuntimeException exception) {
        return ResponseEntity.status(httpStatus)
                             .body(exception.getMessage());
    }
}
