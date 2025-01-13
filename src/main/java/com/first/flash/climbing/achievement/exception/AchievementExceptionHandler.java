package com.first.flash.climbing.achievement.exception;

import com.first.flash.climbing.achievement.exception.exceptions.AchievementAccessDeniedException;
import com.first.flash.climbing.achievement.exception.exceptions.AchievementLimitExceededException;
import com.first.flash.climbing.achievement.exception.exceptions.AchievementNotFoundException;
import com.first.flash.global.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class AchievementExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleAchievementLimitExceededException(
        final AchievementLimitExceededException exception) {
        return getResponseWithStatus(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleAchievementNotFoundException(
        final AchievementNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleAchievementAccessDeniedException(
        final AchievementAccessDeniedException exception) {
        return getResponseWithStatus(HttpStatus.FORBIDDEN, exception);
    }

    private ResponseEntity<ErrorResponseDto> getResponseWithStatus(final HttpStatus httpStatus,
        final RuntimeException exception) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(exception.getMessage());
        return ResponseEntity.status(httpStatus)
                             .body(errorResponse);
    }
}
