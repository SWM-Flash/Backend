package com.first.flash.account.member.application.dto;

import jakarta.validation.constraints.NotEmpty;

public record ConfirmNickNameRequest(@NotEmpty(message = "닉네임은 필수입니다.") String nickName) {

}
