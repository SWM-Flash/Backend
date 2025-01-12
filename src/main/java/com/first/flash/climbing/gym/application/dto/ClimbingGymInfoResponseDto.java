package com.first.flash.climbing.gym.application.dto;

import com.first.flash.climbing.gym.domian.ClimbingGymInfo;
import com.first.flash.climbing.gym.domian.vo.Difficulty;
import java.util.List;

public record ClimbingGymInfoResponseDto(Long id, String gymInfoName,
                                         List<Difficulty> difficulties) {

    public static ClimbingGymInfoResponseDto toDto(final ClimbingGymInfo gym) {
        return new ClimbingGymInfoResponseDto(gym.getId(), gym.getGymInfoName(),
            gym.getDifficulties());
    }
}
