package com.first.flash.account.auth.infrastructure;

import com.first.flash.account.auth.domain.SocialInfoProvider;
import com.first.flash.account.auth.infrastructure.dto.SocialInfo;
import com.first.flash.account.auth.infrastructure.dto.apple.ApplePublicKeys;
import io.jsonwebtoken.Claims;
import java.security.PublicKey;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleSocialInfoProvider implements SocialInfoProvider {

    private static final String CLAIM_SUB = "sub";
    private static final String CLAIM_EMAIL = "email";

    private final AppleTokenParser appleTokenParser;
    private final ApplePublicKeyGenerator applePublicKeyGenerator;
    private final AppleClient appleClient;

    @Override
    public SocialInfo getSocialInfo(final String token) {
        Map<String, String> appleTokenHeader = appleTokenParser.parseHeader(token);
        ApplePublicKeys applePublicKeys = appleClient.getApplePublicKeys();
        PublicKey publicKey = applePublicKeyGenerator.generate(appleTokenHeader, applePublicKeys);
        Claims claims = appleTokenParser.extractClaims(token, publicKey);
        String email = claims.get(CLAIM_EMAIL, String.class);
        String tokenSubject = claims.get(CLAIM_SUB, String.class);
        return SocialInfo.of(email, tokenSubject);
    }
}
