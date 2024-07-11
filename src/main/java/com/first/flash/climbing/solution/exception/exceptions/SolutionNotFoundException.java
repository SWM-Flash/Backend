package com.first.flash.climbing.solution.exception.exceptions;

public class SolutionNotFoundException extends RuntimeException {

    public SolutionNotFoundException(final Long id) {
        super(String.format("아이디가 %s인 해설을 찾을 수 없습니다.", id));
    }
}
