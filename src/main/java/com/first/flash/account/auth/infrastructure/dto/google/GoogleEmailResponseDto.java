package com.first.flash.account.auth.infrastructure.dto.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.first.flash.account.auth.exception.exceptions.EmailRequestFailedException;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GoogleEmailResponseDto(
    @JsonProperty("emailAddresses") List<EmailAddress> emailAddresses) {

    public String getEmail() {
        return emailAddresses.stream()
                             .findFirst()
                             .map(EmailAddress::value)
                             .orElseThrow(EmailRequestFailedException::new);
    }
}
