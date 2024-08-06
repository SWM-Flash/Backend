package com.first.flash.account.auth.exception.exceptions;

public class EmailRequestFailedException extends RuntimeException {

    public EmailRequestFailedException() {
        super("이메일을 가져오는데 실패했습니다.");
    }

    public EmailRequestFailedException(final String message) {
        super(message);
    }
}
