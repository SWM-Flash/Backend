package com.first.flash.account.auth.infrastructure;

import com.first.flash.account.auth.domain.EmailProvider;
import com.first.flash.account.auth.infrastructure.dto.apple.ApplePublicKeys;
import io.jsonwebtoken.Claims;
import java.security.PublicKey;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleEmailProvider implements EmailProvider {

    private static final String CLAIM_SUB = "sub";

    private final AppleTokenParser appleTokenParser;
    private final ApplePublicKeyGenerator applePublicKeyGenerator;
    private final AppleClient appleClient;

    @Override
    public String getEmail(final String token) {
        Map<String, String> appleTokenHeader = appleTokenParser.parseHeader(token);
        ApplePublicKeys applePublicKeys = appleClient.getApplePublicKeys();
        PublicKey publicKey = applePublicKeyGenerator.generate(appleTokenHeader, applePublicKeys);
        Claims claims = appleTokenParser.extractClaims(token, publicKey);
        return claims.get(CLAIM_SUB, String.class);
    }
}
