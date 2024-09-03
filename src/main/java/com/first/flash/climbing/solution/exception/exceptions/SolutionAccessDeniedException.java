package com.first.flash.climbing.solution.exception.exceptions;

public class SolutionAccessDeniedException extends RuntimeException {

    public SolutionAccessDeniedException() {
        super("해당 해설에 접근할 권한이 없습니다.");
    }
}
