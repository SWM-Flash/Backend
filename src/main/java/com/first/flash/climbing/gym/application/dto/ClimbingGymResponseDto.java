package com.first.flash.climbing.gym.application.dto;

import com.first.flash.climbing.gym.domian.ClimbingGym;
import com.first.flash.climbing.gym.domian.vo.Difficulty;
import java.util.List;

public record ClimbingGymResponseDto(String gymName, String thumbnailUrl, String mapImageUrl,
                                     List<Difficulty> difficulties) {

    public static ClimbingGymResponseDto toDto(final ClimbingGym gym) {
        return new ClimbingGymResponseDto(gym.getGymName(), gym.getThumbnailUrl(),
            gym.getMapImageUrl(), gym.getDifficulties());
    }
}
