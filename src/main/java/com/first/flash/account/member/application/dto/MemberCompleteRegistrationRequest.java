package com.first.flash.account.member.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record MemberCompleteRegistrationRequest(@NotEmpty(message = "닉네임은 필수입니다.") @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,20}$", message = "닉네임은 1자에서 20자 사이의 한글, 영문, 숫자만 포함할 수 있습니다.") String nickName,
                                                @Pattern(regexp = "^[a-z0-9._]{2,30}$", message = "인스타그램 아이디는 2자에서 30자 사이의 소문자 영문, 숫자, 밑줄, 온점(.)만 포함할 수 있습니다.") String instagramId,
                                                @Positive(message = "키는 0보다 커야 합니다.") Double height,
                                                @Positive(message = "리치는 0보다 커야 합니다.") Double reach, String profileImageUrl) {

}
