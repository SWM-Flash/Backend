package com.first.flash.account.member.application.dto;

import jakarta.validation.constraints.NotNull;

public record ManageConsentRequest(@NotNull(message = "마케팅 수신 동의 여부는 필수입니다.") Boolean hasAgreedToMarketing) {

}
