package com.first.flash.climbing.gym.application.dto;

import com.first.flash.climbing.gym.domian.ClimbingGym;

public record ClimbingGymResponseDto(Long id, String gymName, String thumbnailUrl) {

    public static ClimbingGymResponseDto toDto(final ClimbingGym gym) {
        return new ClimbingGymResponseDto(gym.getId(), gym.getGymName(), gym.getThumbnailUrl());
    }
}
