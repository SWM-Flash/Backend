package com.first.flash.account.auth.application.dto;

import com.first.flash.account.auth.domain.Provider;
import com.first.flash.global.annotation.ValidEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(@NotEmpty(message = "토큰은 필수입니다.") String token,
                              @NotNull(message = "플랫폼은 필수입니다.") @ValidEnum(enumClass = Provider.class) Provider provider,
                              Boolean hasAgreedToMarketing) {

}
