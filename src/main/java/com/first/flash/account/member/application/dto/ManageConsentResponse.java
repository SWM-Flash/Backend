package com.first.flash.account.member.application.dto;

import com.first.flash.account.member.domain.Member;

public record ManageConsentResponse(boolean hasAgreedToMarketing) {

    public static ManageConsentResponse toDto(final Member member) {
        return new ManageConsentResponse(member.getHasAgreedToMarketing());
    }
}
