package com.first.flash.climbing.solution.application.dto;

import com.first.flash.climbing.solution.domain.PerceivedDifficulty;
import com.first.flash.global.annotation.ValidEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record SolutionUpdateRequestDto(
    @NotEmpty(message = "비디오 URL은 필수입니다.") String videoUrl,
    @Size(max = 500, message = "리뷰는 최대 500자까지 가능합니다.") String review,
    @ValidEnum(enumClass = PerceivedDifficulty.class) PerceivedDifficulty perceivedDifficulty) {

}
