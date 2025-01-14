package com.first.flash.climbing.achievement.exception.exceptions;

public class AchievementAccessDeniedException extends RuntimeException {

    public AchievementAccessDeniedException() {
        super("해당 업적에 접근할 권한이 없습니다.");
    }
}
