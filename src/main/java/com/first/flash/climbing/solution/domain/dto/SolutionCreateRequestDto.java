package com.first.flash.climbing.solution.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SolutionCreateRequestDto(
    @NotEmpty(message = "비디오 URL은 필수입니다.") String videoUrl,
    @NotEmpty(message = "업로더 정보는 필수입니다.") String uploader,
    @NotNull(message = "리뷰 항목은 필수입니다.")
    @Size(max = 500, message = "리뷰는 최대 500자까지 가능합니다.") String review,
    @NotEmpty(message = "인스타그램 ID는 필수입니다.") String instagramId) {

}
