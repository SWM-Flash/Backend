package com.first.flash.climbing.solution.exception.exceptions;

public class SolutionCommentAccessDeniedException extends RuntimeException {

    public SolutionCommentAccessDeniedException() {
        super("해당 댓글에 접근할 권한이 없습니다.");
    }
}
