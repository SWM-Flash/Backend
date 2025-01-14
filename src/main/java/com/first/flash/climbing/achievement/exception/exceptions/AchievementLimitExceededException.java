package com.first.flash.climbing.achievement.exception.exceptions;

public class AchievementLimitExceededException extends RuntimeException {

    public AchievementLimitExceededException() {
        super("최대 업적 개수는 2개입니다.");
    }
}
