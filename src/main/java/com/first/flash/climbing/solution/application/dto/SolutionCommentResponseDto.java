package com.first.flash.climbing.solution.application.dto;

import com.first.flash.climbing.solution.domain.SolutionComment;

public record SolutionCommentResponseDto() {

    public static SolutionCommentResponseDto toDto(final SolutionComment solutionComment) {
        return new SolutionCommentResponseDto();
    }
}
