package com.first.flash.climbing.problem.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProblemCreateRequestDto(
    @NotEmpty(message = "이미지 URL은 필수입니다.") String imageUrl,
    @NotEmpty(message = "난이도는 필수입니다.") String difficulty,
    @NotEmpty(message = "이미지 출처는 필수입니다.") String imageSource,
    @NotNull(message = "썸네일 해설 아이디는 필수입니다.") Long thumbnailSolutionId,
    @NotNull(message = "홀드 아이디는 필수입니다.") Long holdId) {

}
