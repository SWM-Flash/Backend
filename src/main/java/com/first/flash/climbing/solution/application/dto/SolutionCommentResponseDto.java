package com.first.flash.climbing.solution.application.dto;

import com.first.flash.climbing.solution.domain.SolutionComment;
import com.first.flash.global.util.AuthUtil;
import java.util.UUID;

public record SolutionCommentResponseDto(Long id, String content, UUID commenterId, String nickName,
                                         String profileImageUrl, boolean isMine) {

    public static SolutionCommentResponseDto toDto(final SolutionComment solutionComment) {
        return new SolutionCommentResponseDto(solutionComment.getId(), solutionComment.getContent(),
            solutionComment.getCommenterDetail().getCommenterId(),
            solutionComment.getCommenterDetail().getCommenter(),
            solutionComment.getCommenterDetail().getProfileImageUrl(),
            solutionComment.getCommenterDetail().getCommenterId().equals(AuthUtil.getId())
        );
    }
}
