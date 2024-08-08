package com.first.flash.account.auth.infrastructure.dto.apple;

public record ApplePublicKey(String kty,
                             String kid,
                             String alg,
                             String n,
                             String e) {

    public boolean isSameAlg(final String alg) {
        return this.alg.equals(alg);
    }

    public boolean isSameKid(final String kid) {
        return this.kid.equals(kid);
    }
}
