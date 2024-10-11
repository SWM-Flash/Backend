package com.first.flash.climbing.problem.application.dto;

import jakarta.validation.constraints.NotNull;

public record ProblemPerceivedDifficultyRequestDto(
    @NotNull(message = "변경할 체감 난이도 수치는 필수입니다.") Integer perceivedDifficulty) {

}
