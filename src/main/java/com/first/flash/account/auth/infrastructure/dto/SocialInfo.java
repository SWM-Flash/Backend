package com.first.flash.account.auth.infrastructure.dto;

import java.util.Objects;

public record SocialInfo(String email, String socialId) {

    public static SocialInfo from(final String email) {
        return new SocialInfo(email, email);
    }

    public static SocialInfo of(final String email, final String socialId) {
        return new SocialInfo(email, socialId);
    }

    public boolean hasEmail() {
        return Objects.isNull(email);
    }
}
