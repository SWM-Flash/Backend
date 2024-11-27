package com.first.flash.climbing.gym.infrastructure.dto;

import com.first.flash.climbing.gym.domian.ClimbingGym;
import java.util.List;

public record ClimbingGymResponseDto(Long id, String gymName, String thumbnailUrl, boolean isFavorite) {

    public static Object toDto(final ClimbingGym gym, final List<Long> favoriteGymIds) {
        return new ClimbingGymResponseDto(gym.getId(), gym.getGymName(), gym.getThumbnailUrl(), favoriteGymIds.contains(gym.getId()));
    }
}
