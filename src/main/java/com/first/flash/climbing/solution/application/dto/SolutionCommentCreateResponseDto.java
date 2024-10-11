package com.first.flash.climbing.solution.application.dto;

import com.first.flash.climbing.solution.domain.SolutionComment;
import java.util.UUID;

public record SolutionCommentCreateResponseDto(Long id, String content, UUID commenterId,
                                               String nickName, String profileImageUrl) {

    public static SolutionCommentCreateResponseDto toDto(
        final SolutionComment comment) {
        return new SolutionCommentCreateResponseDto(comment.getId(), comment.getContent(),
            comment.getCommenterDetail().getCommenterId(),
            comment.getCommenterDetail().getCommenter(),
            comment.getCommenterDetail().getProfileImageUrl());
    }
}
