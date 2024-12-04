package com.first.flash.climbing.problem.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProblemCreateRequestDto(
    String imageUrl,
    String imageSource,
    Long thumbnailSolutionId,
    @NotEmpty(message = "난이도는 필수입니다.") String difficulty,
    @NotNull(message = "홀드 아이디는 필수입니다.") Long holdId) {

}
