package com.first.flash.climbing.problem.application.dto;

import jakarta.validation.constraints.NotNull;

public record ProblemHoldRequestDto(
    @NotNull(message = "변경할 홀드 id는 필수입니다.") Long holdId) {

}
