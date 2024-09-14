package com.first.flash.global.config;

import com.first.flash.account.auth.domain.TokenManager;
import com.first.flash.global.filter.JwtAuthFilter;
import com.first.flash.global.filter.TokenExceptionFilter;
import com.first.flash.global.filter.handler.CustomAccessDeniedHandler;
import com.first.flash.global.filter.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
        "/auth/login",
        "/swagger-ui/*",
        "/v1/api-docs/**",
        "/flash-climbing-answer-health/**"
    };
    private static final String COMPLETE_REGISTRATION = "/members";
    private static final String MARKETING_CONSENT = "/members/marketing-consent";


    private final TokenManager tokenManager;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                   .cors(cors -> cors.configurationSource(request -> {
                       var corsConfiguration = new CorsConfiguration();
                       corsConfiguration.addAllowedOrigin("*");
                       corsConfiguration.addAllowedHeader("*");
                       corsConfiguration.addAllowedMethod("*");
                       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                       source.registerCorsConfiguration("/**", corsConfiguration);
                       return corsConfiguration;
                   }))
                   .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                       SessionCreationPolicy.STATELESS))
                   .formLogin(AbstractHttpConfigurer::disable)
                   .httpBasic(AbstractHttpConfigurer::disable)
                   .logout(AbstractHttpConfigurer::disable)
                   .exceptionHandling(this::configureExceptionHandler)
                   .authorizeHttpRequests(authorize -> authorize
                       .requestMatchers(AUTH_WHITELIST).permitAll()
                       .requestMatchers("/admin/**").hasRole("ADMIN")
                       .requestMatchers(HttpMethod.POST, MARKETING_CONSENT)
                       .hasAnyRole("ADMIN", "USER", "UNREGISTERED_USER")
                       .requestMatchers(HttpMethod.PATCH, COMPLETE_REGISTRATION)
                       .hasAnyRole("ADMIN", "USER", "UNREGISTERED_USER")
                       .requestMatchers(HttpMethod.GET, "/**").hasAnyRole("ADMIN", "USER", "WEB")
                       .requestMatchers("/**").hasAnyRole("ADMIN", "USER")
                       .anyRequest().authenticated()
                   )
                   .addFilterBefore(new JwtAuthFilter(tokenManager, userDetailsService),
                       UsernamePasswordAuthenticationFilter.class)
                   .addFilterBefore(new TokenExceptionFilter(), JwtAuthFilter.class)
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
