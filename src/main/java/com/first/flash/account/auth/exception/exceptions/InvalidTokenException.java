package com.first.flash.account.auth.exception.exceptions;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("유효하지 않은 토큰입니다!");
    }
}
