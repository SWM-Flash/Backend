package com.first.flash.account.auth.config;

import com.first.flash.account.auth.domain.EmailProvider;
import com.first.flash.account.auth.domain.Provider;
import com.first.flash.account.auth.infrastructure.AppleEmailProvider;
import com.first.flash.account.auth.infrastructure.GoogleEmailProvider;
import com.first.flash.account.auth.infrastructure.KakaoEmailProvider;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProviderConfig {

    @Bean
    public Map<Provider, EmailProvider> providers(final GoogleEmailProvider googleEmailProvider,
        final KakaoEmailProvider kakaoEmailProvider, final AppleEmailProvider appleEmailProvider) {
        return Map.of(
            Provider.GOOGLE, googleEmailProvider,
            Provider.KAKAO, kakaoEmailProvider,
            Provider.APPLE, appleEmailProvider
        );
    }
}
