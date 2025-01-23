package com.first.flash.upload.exception;

import com.first.flash.global.dto.ErrorResponseDto;
import com.first.flash.upload.exception.exceptions.FileStoreFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UploadExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleFileStoreFailedException(final
    FileStoreFailedException exception) {
        return getResponseWithStatus(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    private ResponseEntity<ErrorResponseDto> getResponseWithStatus(final HttpStatus httpStatus,
        final RuntimeException exception) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(exception.getMessage());
        return ResponseEntity.status(httpStatus)
                             .body(errorResponse);
    }
}
