package com.first.flash.account.member.exception;

import com.first.flash.account.member.exception.exceptions.MemberNotFoundException;
import com.first.flash.account.member.exception.exceptions.NickNameDuplicatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleMemberNotFoundException(
        final MemberNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNickNameDuplicatedException(
        final NickNameDuplicatedException exception) {
        return getResponseWithStatus(HttpStatus.CONFLICT, exception);
    }

    private ResponseEntity<String> getResponseWithStatus(final HttpStatus httpStatus,
        final RuntimeException exception) {
        return ResponseEntity.status(httpStatus)
                             .body(exception.getMessage());
    }
}
