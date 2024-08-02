package com.first.flash.climbing.gym.exception.exceptions;

public class DuplicateDifficultyLevelException extends RuntimeException {

    public DuplicateDifficultyLevelException(final Integer difficultyLevel) {
        super(String.format("난이도 레벨이 중복되었습니다: %d", difficultyLevel));
    }
}
