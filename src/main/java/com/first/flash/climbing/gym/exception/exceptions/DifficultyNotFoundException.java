package com.first.flash.climbing.gym.exception.exceptions;

public class DifficultyNotFoundException extends RuntimeException {

    public DifficultyNotFoundException(final String difficultyName) {
        super(String.format("이름이 %s인 난이도를 찾을 수 없습니다.", difficultyName));
    }
}
