package com.first.flash.climbing.solution.exception.exceptions;

public class PerceivedDifficultyNotFoundException extends RuntimeException {

    public PerceivedDifficultyNotFoundException(final String label) {
        super(String.format("해당하는 체감 난이도가 없습니다: %s", label));
    }
}