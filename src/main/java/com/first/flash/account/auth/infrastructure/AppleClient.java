package com.first.flash.account.auth.infrastructure;

import com.first.flash.account.auth.infrastructure.dto.apple.ApplePublicKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AppleClient {

    private static final String APPLE_PUBLIC_KEYS_URL = "https://appleid.apple.com/auth/keys";

    private final RestTemplate restTemplate;

    public ApplePublicKeys getApplePublicKeys() {
        return restTemplate.getForObject(APPLE_PUBLIC_KEYS_URL, ApplePublicKeys.class);
    }
}
