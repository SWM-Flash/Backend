package com.first.flash.climbing.solution.application.dto;

import jakarta.validation.constraints.NotNull;

public record SolutionCommentUpdateRequestDto(@NotNull String content) {

}
