package com.first.flash.climbing.achievement.application.dto;

import com.first.flash.climbing.achievement.domain.Achievement;

public record AchievementResponseDto(Long id, Long solveCount, String difficultyName,
                                     String gymName) {

    public static AchievementResponseDto toDto(final Achievement achievement) {
        return new AchievementResponseDto(achievement.getId(), achievement.getSolveCount(),
            achievement.getDifficultyName(), achievement.getGymName());
    }
}
