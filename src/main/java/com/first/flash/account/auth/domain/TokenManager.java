package com.first.flash.account.auth.domain;

import java.util.UUID;

public interface TokenManager {

    String createAccessToken(final UUID id);
    boolean validateToken(final String token);
    UUID parseToken(final String token);
}
