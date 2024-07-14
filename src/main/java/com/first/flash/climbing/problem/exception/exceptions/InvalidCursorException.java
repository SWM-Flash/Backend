package com.first.flash.climbing.problem.exception.exceptions;

public class InvalidCursorException extends RuntimeException {

    public InvalidCursorException() {
        super("유효하지 않은 커서입니다.");
    }
}
