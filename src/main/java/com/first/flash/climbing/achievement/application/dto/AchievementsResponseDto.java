package com.first.flash.climbing.achievement.application.dto;

import java.util.List;

public record AchievementsResponseDto(List<AchievementResponseDto> achievements) {

    public static AchievementsResponseDto toDto(final List<AchievementResponseDto> achievements) {
        return new AchievementsResponseDto(achievements);
    }
}
