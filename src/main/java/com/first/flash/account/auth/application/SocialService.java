package com.first.flash.account.auth.application;

import com.first.flash.account.auth.domain.SocialInfoProvider;
import com.first.flash.account.auth.domain.Provider;
import com.first.flash.account.auth.infrastructure.dto.SocialInfo;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialService {

    private final Map<Provider, SocialInfoProvider> providers;

    public SocialInfo getSocialInfo(final Provider provider, final String token) {
        return providers.get(provider).getSocialInfo(token);
    }
}
