package com.first.flash.account.auth.exception.exceptions;

public class InvalidAppleKeyException extends RuntimeException {

    public InvalidAppleKeyException() {
        super("유효하지 않은 Apple 공개키입니다!");
    }
}
