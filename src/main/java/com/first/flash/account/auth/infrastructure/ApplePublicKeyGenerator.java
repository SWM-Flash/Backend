package com.first.flash.account.auth.infrastructure;

import com.first.flash.account.auth.exception.exceptions.InvalidAppleKeyException;
import com.first.flash.account.auth.infrastructure.dto.apple.ApplePublicKey;
import com.first.flash.account.auth.infrastructure.dto.apple.ApplePublicKeys;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplePublicKeyGenerator {

    private static final String SIGN_ALGORITHM_HEADER = "alg";
    private static final String KEY_ID_HEADER = "kid";
    private static final int POSITIVE_SIGN_NUMBER = 1;

    public PublicKey generate(final Map<String, String> headers, final ApplePublicKeys publicKeys) {
        final ApplePublicKey applePublicKey = publicKeys.getMatchingKey(
            headers.get(SIGN_ALGORITHM_HEADER),
            headers.get(KEY_ID_HEADER)
        );
        return generatePublicKey(applePublicKey);
    }

    private PublicKey generatePublicKey(final ApplePublicKey applePublicKey) {
        final byte[] nBytes = Base64.getUrlDecoder().decode(applePublicKey.n());
        final byte[] eBytes = Base64.getUrlDecoder().decode(applePublicKey.e());

        final BigInteger n = new BigInteger(POSITIVE_SIGN_NUMBER, nBytes);
        final BigInteger e = new BigInteger(POSITIVE_SIGN_NUMBER, eBytes);
        final RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(n, e);

        try {
            final KeyFactory keyFactory = KeyFactory.getInstance(applePublicKey.kty());
            return keyFactory.generatePublic(rsaPublicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new InvalidAppleKeyException();
        }
    }
}
