package com.first.flash.account.auth.application;

import com.first.flash.account.auth.domain.EmailProvider;
import com.first.flash.account.auth.domain.Provider;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialService {

    private final Map<Provider, EmailProvider> providers;

    public String getEmail(final Provider provider, final String token) {
        return providers.get(provider).getEmail(token);
    }
}
