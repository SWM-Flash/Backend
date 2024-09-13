package com.first.flash.account.auth.exception.exceptions;

public class MarketingConsentRequiredException extends RuntimeException{

    public MarketingConsentRequiredException() {
        super("회원가입 시 마케팅 수신 동의 여부를 선택해야 합니다.");
    }
}
