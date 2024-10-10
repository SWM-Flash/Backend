package com.first.flash.climbing.solution.application.dto;

import com.first.flash.climbing.solution.domain.SolutionComment;

public record SolutionCommentCreateResponseDto() {

    public static SolutionCommentCreateResponseDto toDto(
        final SolutionComment savedSolutionComment) {
        return new SolutionCommentCreateResponseDto();
    }
}
