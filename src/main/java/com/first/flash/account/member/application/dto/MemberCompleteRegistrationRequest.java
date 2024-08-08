package com.first.flash.account.member.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record MemberCompleteRegistrationRequest(@NotEmpty(message = "닉네임은 필수입니다.") String nickName, String instagramId,
                                                @Positive(message = "키는 0보다 커야 합니다.") Double height,
                                                @Positive(message = "리치는 0보다 커야 합니다.") Double reach, String profileImageUrl) {

}
