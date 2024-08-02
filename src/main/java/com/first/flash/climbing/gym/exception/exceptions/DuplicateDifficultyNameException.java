package com.first.flash.climbing.gym.exception.exceptions;

public class DuplicateDifficultyNameException extends RuntimeException {

    public DuplicateDifficultyNameException(final String difficultyName) {
        super(String.format("난이도 이름이 중복되었습니다: %s", difficultyName));
    }
}
