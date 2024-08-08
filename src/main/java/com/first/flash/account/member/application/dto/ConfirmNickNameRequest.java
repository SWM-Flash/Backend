package com.first.flash.account.member.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record ConfirmNickNameRequest(
    @NotEmpty(message = "닉네임은 필수입니다.") @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,20}$", message = "닉네임은 1자에서 20자 사이의 한글, 영문, 숫자만 포함할 수 있습니다.") String nickName) {

}
