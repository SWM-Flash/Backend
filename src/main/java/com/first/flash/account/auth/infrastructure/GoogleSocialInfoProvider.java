package com.first.flash.account.auth.infrastructure;

import com.first.flash.account.auth.domain.SocialInfoProvider;
import com.first.flash.account.auth.exception.exceptions.SocialRequestFailedException;
import com.first.flash.account.auth.infrastructure.dto.SocialInfo;
import com.first.flash.account.auth.infrastructure.dto.google.GoogleEmailResponseDto;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleSocialInfoProvider implements SocialInfoProvider {

    private static final String GOOGLE_API_URL = "https://oauth2.googleapis.com/tokeninfo";
    private static final String TOKEN_KEY = "access_token";

    private final RestTemplate restTemplate;

    @Override
    public SocialInfo getSocialInfo(final String token) {
        String uri = prepareRequest(token);
        String email = requestEmail(uri);
        return SocialInfo.from(email);
    }

    private String requestEmail(final String uri) {
        try {
            ResponseEntity<GoogleEmailResponseDto> response = restTemplate.getForEntity(uri,
                GoogleEmailResponseDto.class);
            return resolveResponse(response);
        } catch (final RuntimeException exception) {
            throw new SocialRequestFailedException(exception.getMessage());
        }
    }

    private static String resolveResponse(final ResponseEntity<GoogleEmailResponseDto> response) {
        if (isSuccessfulResponse(response)) {
            String email = response.getBody()
                                   .getEmail();
            log.info("Google email: {}", email);
            return email;
        } else {
            throw new SocialRequestFailedException();
        }
    }

    private static boolean isSuccessfulResponse(
        final ResponseEntity<GoogleEmailResponseDto> response) {
        return response.getStatusCode().equals(HttpStatus.OK) &&
            !Objects.isNull(response.getBody());
    }

    private String prepareRequest(final String token) {
        return UriComponentsBuilder.fromHttpUrl(GOOGLE_API_URL)
                                   .queryParam(TOKEN_KEY, token)
                                   .build()
                                   .toUriString();
    }
}
