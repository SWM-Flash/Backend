package com.first.flash.global.filter;

import com.first.flash.account.auth.domain.TokenManager;
import com.first.flash.account.auth.exception.exceptions.InvalidTokenException;
import com.first.flash.account.auth.exception.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_HEADER_PREFIX = "Bearer ";
    private static final int AUTH_HEADER_PREFIX_LENGTH = AUTH_HEADER_PREFIX.length();

    private final TokenManager tokenManager;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
        final HttpServletResponse response,
        final FilterChain filterChain) throws ServletException, IOException {
        log.debug("처리 중인 URI: {}", request.getRequestURI());
        try {
            String token = resolveToken(request);
            UUID id = tokenManager.parseToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(id.toString());
            setAuthentication(userDetails);
        } catch (TokenExpiredException exception) {
            throw exception;
        } catch (RuntimeException exception) {
            throw new AuthenticationServiceException(exception.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(final UserDetails userDetails) {
        UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private String resolveToken(final HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH_HEADER);
        if (Objects.isNull(bearerToken) || !bearerToken.startsWith(AUTH_HEADER_PREFIX)) {
            log.debug("토큰이 없음: {}", bearerToken);
            throw new InvalidTokenException();
        }
        String token = bearerToken.substring(AUTH_HEADER_PREFIX_LENGTH);
        if (!tokenManager.validateToken(token)) {
            log.debug("토큰이 유효하지 않음: {}", token);
            throw new InvalidTokenException();
        }
        return token;
    }
}
