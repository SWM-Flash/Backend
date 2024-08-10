package com.first.flash.account.auth.exception.exceptions;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super("만료된 토큰입니다!");
    }
}
