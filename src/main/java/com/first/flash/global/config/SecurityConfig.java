package com.first.flash.global.config;

import com.first.flash.account.auth.domain.TokenManager;
import com.first.flash.global.filter.handler.CustomAccessDeniedHandler;
import com.first.flash.global.filter.handler.CustomAuthenticationEntryPoint;
import com.first.flash.global.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
        "/login",
    };

    private final TokenManager tokenManager;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                   .cors(AbstractHttpConfigurer::disable)
                   .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                       SessionCreationPolicy.STATELESS))
                   .formLogin(AbstractHttpConfigurer::disable)
                   .httpBasic(AbstractHttpConfigurer::disable)
                   .logout(AbstractHttpConfigurer::disable)
                   .exceptionHandling(this::configureExceptionHandler)
                   .authorizeHttpRequests(authorize -> authorize
                       .requestMatchers(AUTH_WHITELIST).permitAll()
                       .requestMatchers("/admin/**").hasRole("ADMIN")
                       .anyRequest().authenticated()
                   )
                   .addFilterBefore(new JwtAuthFilter(tokenManager, userDetailsService),
                       UsernamePasswordAuthenticationFilter.class)
                   .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                         .requestMatchers(AUTH_WHITELIST);
    }

    private void configureExceptionHandler(
        final ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling) {
        exceptionHandling
            .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}