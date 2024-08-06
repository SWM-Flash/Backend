package com.first.flash.account.auth.infrastructure;

import com.first.flash.account.auth.domain.TokenManager;
import com.first.flash.account.auth.exception.exceptions.InvalidTokenException;
import com.first.flash.account.auth.exception.exceptions.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtManager implements TokenManager {

    @Value("${auth.jwt.secret}")
    private String secretKey;
    @Value("${auth.jwt.expiration}")
    private Long expiredSecond;
    private SecretKey key;

    @PostConstruct
    private void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Override
    public String createAccessToken(final UUID id) {
        Claims claim = Jwts.claims()
                           .subject(id.toString())
                           .build();
        Date now = new Date();
        Date expiredIn = new Date(now.getTime() + expiredSecond);

        return Jwts.builder()
                   .claims(claim)
                   .issuedAt(now)
                   .expiration(expiredIn)
                   .signWith(key, SIG.HS512)
                   .compact();
    }

    @Override
    public boolean validateToken(final String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        Claims claims = parseClaims(token);
        return claims.getExpiration()
                     .after(new Date());
    }

    @Override
    public UUID parseToken(final String token) {
        Claims claims = parseClaims(token);
        return UUID.fromString(claims.getSubject());
    }

    private Claims parseClaims(final String token) {
        try {
            return Jwts.parser()
                       .verifyWith(key)
                       .build()
                       .parseSignedClaims(token)
                       .getPayload();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();
        } catch (RuntimeException e) {
            throw new InvalidTokenException();
        }
    }
}
