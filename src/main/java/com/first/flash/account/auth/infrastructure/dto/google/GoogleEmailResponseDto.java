package com.first.flash.account.auth.infrastructure.dto.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GoogleEmailResponseDto(
    @JsonProperty("email") String email) {

    public String getEmail() {
        return email;
    }
}
