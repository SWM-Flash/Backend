package com.first.flash.global.filter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.first.flash.global.filter.handler.dto.DeniedExceptionDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response,
        final AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        DeniedExceptionDto defaultException = DeniedExceptionDto.createDefault();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(defaultException);
        response.getWriter().write(jsonResponse);
    }
}
