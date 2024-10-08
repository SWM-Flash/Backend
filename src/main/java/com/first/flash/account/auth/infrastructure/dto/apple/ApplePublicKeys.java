package com.first.flash.account.auth.infrastructure.dto.apple;

import java.util.List;

public record ApplePublicKeys(List<ApplePublicKey> keys) {

    public ApplePublicKey getMatchingKey(final String alg, final String kid) {
        return keys.stream()
                   .filter(key -> key.isSameAlg(alg) && key.isSameKid(kid))
                   .findFirst()
                   .orElseThrow(() -> new RuntimeException("잘못된 토큰 형태입니다."));
    }
}
