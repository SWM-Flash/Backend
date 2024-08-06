package com.first.flash.account.auth.infrastructure.dto.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KaKaoAcount(@JsonProperty("email") String email) {

}
