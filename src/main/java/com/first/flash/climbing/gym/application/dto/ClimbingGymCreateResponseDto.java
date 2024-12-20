package com.first.flash.climbing.gym.application.dto;

import com.first.flash.climbing.gym.domian.ClimbingGym;
import com.first.flash.climbing.gym.domian.vo.Difficulty;
import java.util.List;

public record ClimbingGymCreateResponseDto(Long id, String gymName, String thumbnailUrl,
                                           String mapImageUrl, String calendarImageUrl, List<Difficulty> difficulties) {

    public static ClimbingGymCreateResponseDto toDto(final ClimbingGym gym) {
        return new ClimbingGymCreateResponseDto(gym.getId(), gym.getGymName(),
            gym.getThumbnailUrl(), gym.getMapImageUrl(), gym.getCalendarImageUrl(), gym.getDifficulties());
    }
}
