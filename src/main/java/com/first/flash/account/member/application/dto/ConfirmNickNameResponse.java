package com.first.flash.account.member.application.dto;

public record ConfirmNickNameResponse(boolean isAvailable) {

    public static ConfirmNickNameResponse from(final boolean isDuplicated) {
        return new ConfirmNickNameResponse(!isDuplicated);
    }
}
