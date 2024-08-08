package com.first.flash.account.auth.application.dto;

import com.first.flash.global.annotation.ValidProvider;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequestDto(@NotEmpty(message = "토큰은 필수입니다.") String token,
                              @ValidProvider String provider) {

}
