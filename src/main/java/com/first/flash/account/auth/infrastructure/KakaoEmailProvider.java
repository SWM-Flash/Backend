package com.first.flash.account.auth.infrastructure;

import com.first.flash.account.auth.domain.EmailProvider;
import com.first.flash.account.auth.exception.exceptions.EmailRequestFailedException;
import com.first.flash.account.auth.infrastructure.dto.kakao.KakaoEmailResponseDto;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoEmailProvider implements EmailProvider {

    private static final String KAKAO_API_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_HEADER_PREFIX = "Bearer ";

    private final RestTemplate restTemplate;

    @Override
    public String getEmail(final String token) {
        HttpEntity<String> httpEntity = prepareRequest(token);
        return requestEmail(httpEntity);
    }

    private String requestEmail(final HttpEntity<String> httpEntity) {
        try {
            ResponseEntity<KakaoEmailResponseDto> response = restTemplate.exchange(KAKAO_API_URL,
                HttpMethod.GET, httpEntity, KakaoEmailResponseDto.class);
            return resolveResponse(response);
        } catch (Exception e) {
            throw new EmailRequestFailedException(e.getMessage());
        }
    }

    private static String resolveResponse(final ResponseEntity<KakaoEmailResponseDto> response) {
        if (isSuccessfulResponse(response)) {
            return response.getBody()
                           .getEmail();
        } else {
            throw new EmailRequestFailedException();
        }
    }

    private static boolean isSuccessfulResponse(
        final ResponseEntity<KakaoEmailResponseDto> response) {
        return response.getStatusCode().equals(HttpStatus.OK) &&
            !Objects.isNull(response.getBody());
    }

    private HttpEntity<String> prepareRequest(final String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER, AUTH_HEADER_PREFIX + token);
        return new HttpEntity<>(headers);
    }
}