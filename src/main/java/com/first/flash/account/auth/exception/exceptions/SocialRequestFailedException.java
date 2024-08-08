package com.first.flash.account.auth.exception.exceptions;

public class SocialRequestFailedException extends RuntimeException {

    public SocialRequestFailedException() {
        super("이메일을 가져오는데 실패했습니다.");
    }

    public SocialRequestFailedException(final String message) {
        super(message);
    }
}
