package com.first.flash.climbing.solution.domain.dto;

import com.first.flash.climbing.solution.domain.PerceivedDifficulty;
import com.first.flash.global.annotation.ValidEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record SolutionCreateRequestDto(
    @NotEmpty(message = "비디오 URL은 필수입니다.") String videoUrl,
    @Size(max = 500, message = "리뷰는 최대 500자까지 가능합니다.") String review,
    @ValidEnum(enumClass = PerceivedDifficulty.class) PerceivedDifficulty perceivedDifficulty) {

    public SolutionCreateRequestDto(final String videoUrl, final String review, final PerceivedDifficulty perceivedDifficulty) {
        this.videoUrl = videoUrl;
        this.review = review;
        this.perceivedDifficulty = perceivedDifficulty != null ? perceivedDifficulty : PerceivedDifficulty.NORMAL;
    }
}
