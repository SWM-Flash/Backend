package com.first.flash.climbing.solution.application.dto;

import java.util.List;

public record SolutionCommentsResponseDto(List<SolutionCommentResponseDto> comments) {

    public static SolutionCommentsResponseDto from(
        final List<SolutionCommentResponseDto> commentsResponse) {
        return new SolutionCommentsResponseDto(commentsResponse);
    }
}
