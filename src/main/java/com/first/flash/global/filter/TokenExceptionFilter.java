package com.first.flash.global.filter;

import com.first.flash.account.auth.exception.exceptions.InvalidTokenException;
import com.first.flash.account.auth.exception.exceptions.TokenExpiredException;
import com.first.flash.account.member.exception.exceptions.MemberNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;

public class TokenExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
        final HttpServletResponse response,
        final FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (final TokenExpiredException | InvalidTokenException exception) {
            handleResponse(response, HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
        } catch (final MemberNotFoundException exception) {
            handleResponse(response, HttpServletResponse.SC_NOT_FOUND, exception.getMessage());
        }
    }

    private static void handleResponse(final HttpServletResponse response, final int scNotFound,
        final String exception) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(scNotFound);
        response.getWriter().write(exception);
    }
}
