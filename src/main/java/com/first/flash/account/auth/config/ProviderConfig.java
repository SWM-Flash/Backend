package com.first.flash.account.auth.config;

import com.first.flash.account.auth.domain.SocialInfoProvider;
import com.first.flash.account.auth.domain.Provider;
import com.first.flash.account.auth.infrastructure.AppleSocialInfoProvider;
import com.first.flash.account.auth.infrastructure.GoogleSocialInfoProvider;
import com.first.flash.account.auth.infrastructure.KakaoSocialInfoProvider;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProviderConfig {

    @Bean
    public Map<Provider, SocialInfoProvider> providers(final GoogleSocialInfoProvider googleEmailProvider,
        final KakaoSocialInfoProvider kakaoEmailProvider, final AppleSocialInfoProvider appleEmailProvider) {
        return Map.of(
            Provider.GOOGLE, googleEmailProvider,
            Provider.KAKAO, kakaoEmailProvider,
            Provider.APPLE, appleEmailProvider
        );
    }
}
