package com.first.flash.climbing.problem.domain.dto;

import jakarta.validation.constraints.NotEmpty;

public record ProblemCreateRequestDto(
    @NotEmpty(message = "이미지 URL은 필수입니다.") String imageUrl,
    @NotEmpty(message = "난이도는 필수입니다.") String difficulty) {

}
