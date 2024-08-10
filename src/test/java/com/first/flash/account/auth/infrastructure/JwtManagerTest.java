package com.first.flash.account.auth.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.first.flash.account.auth.domain.TokenManager;
import com.first.flash.account.auth.exception.exceptions.TokenExpiredException;
import io.jsonwebtoken.security.Keys;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JwtManagerTest {

    private TokenManager tokenManager;

    @BeforeEach
    void init() {
        tokenManager = new JwtManager();
        String testSecretKey = "dS5kuWLwxI8xnL/29JorFY1OM/ku4e7tOC+ryBs1WFx3bcZRHBgzWTMIklrx0b4GIljq+28fVZKQ+clx";
        ReflectionTestUtils.setField(tokenManager, "secretKey", testSecretKey);
        ReflectionTestUtils.setField(tokenManager, "expiredSecond", 1800000L);
        ReflectionTestUtils.setField(tokenManager, "key",
            Keys.hmacShaKeyFor(testSecretKey.getBytes()));
    }

    @Test
    void 토큰_생성_파싱() {
        // given
        UUID id = UUID.randomUUID();
        String accessToken = tokenManager.createAccessToken(id);

        // when
        boolean isValid = tokenManager.validateToken(accessToken);
        UUID parsedId = tokenManager.parseToken(accessToken);

        // then
        assertThat(parsedId).isEqualTo(id);
    }

    @Test
    void 토큰_검증() {
        // given
        UUID id = UUID.randomUUID();
        String accessToken = tokenManager.createAccessToken(id);

        // when
        boolean isValid = tokenManager.validateToken(accessToken);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    void 토큰_만료_예외() {
        // given
        ReflectionTestUtils.setField(tokenManager, "expiredSecond", -1L);
        UUID id = UUID.randomUUID();
        String accessToken = tokenManager.createAccessToken(id);

        // when & then
        assertThatThrownBy(() -> tokenManager.parseToken(accessToken))
            .isInstanceOf(TokenExpiredException.class);
    }
}
