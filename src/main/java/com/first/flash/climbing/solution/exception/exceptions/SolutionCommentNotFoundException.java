package com.first.flash.climbing.solution.exception.exceptions;

public class SolutionCommentNotFoundException extends RuntimeException {

    public SolutionCommentNotFoundException(final Long id) {
        super(String.format("아이디가 %s인 댓글을 찾을 수 없습니다.", id));
    }
}
