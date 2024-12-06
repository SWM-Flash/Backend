package com.first.flash.climbing.solution.application.dto;

import com.first.flash.climbing.solution.domain.PerceivedDifficulty;
import com.first.flash.global.annotation.ValidEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record SolutionUpdateRequestDto(
    @NotEmpty(message = "썸네일 URL은 필수입니다.") String thumbnailImageUrl,
    @NotEmpty(message = "비디오 URL은 필수입니다.") String videoUrl,
    @Size(max = 500, message = "리뷰는 최대 500자까지 가능합니다.") String review,
    @NotNull(message = "풀이 일자 정보는 필수입니다.") LocalDate solvedDate,
    @NotNull(message = "체감 난이도는 필수입니다.")
    @ValidEnum(enumClass = PerceivedDifficulty.class) PerceivedDifficulty perceivedDifficulty) {

}
