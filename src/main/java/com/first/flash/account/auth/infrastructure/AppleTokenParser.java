package com.first.flash.account.auth.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.first.flash.account.auth.exception.exceptions.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleTokenParser {

    private static final String IDENTITY_TOKEN_VALUE_DELIMITER = "\\.";
    private static final int HEADER_INDEX = 0;

    private final ObjectMapper objectMapper;

    public Map<String, String> parseHeader(final String appleToken) {
        try {
            final String encodedHeader = appleToken.split(
                IDENTITY_TOKEN_VALUE_DELIMITER)[HEADER_INDEX];
            final String decodedHeader = new String(Base64.getDecoder()
                                                          .decode(encodedHeader.getBytes()));
            return objectMapper.readValue(decodedHeader, Map.class);
        } catch (JsonProcessingException e) {
            throw new InvalidTokenException();
        }
    }

    public Claims extractClaims(final String appleToken, final PublicKey publicKey) {
        try {
            return Jwts.parser()
                       .verifyWith(publicKey)
                       .build()
                       .parseSignedClaims(appleToken)
                       .getPayload();
        } catch (RuntimeException e) {
            throw new InvalidTokenException();
        }
    }
}
