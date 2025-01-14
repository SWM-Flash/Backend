package com.first.flash.climbing.achievement.exception.exceptions;

public class AchievementNotFoundException extends RuntimeException {

    public AchievementNotFoundException(final Long id) {
        super(String.format("아이디가 %s인 업적을 찾을 수 없습니다.", id));
    }

    public AchievementNotFoundException() {
        super("업적을 찾을 수 없습니다.");
    }
}
