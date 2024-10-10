package com.first.flash.climbing.solution.application.dto;

import jakarta.validation.constraints.NotEmpty;

public record SolutionCommentCreateRequestDto(@NotEmpty String content) {

}
