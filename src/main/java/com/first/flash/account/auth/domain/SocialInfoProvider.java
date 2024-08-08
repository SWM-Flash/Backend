package com.first.flash.account.auth.domain;

import com.first.flash.account.auth.infrastructure.dto.SocialInfo;

public interface SocialInfoProvider {

    SocialInfo getSocialInfo(final String token);
}
