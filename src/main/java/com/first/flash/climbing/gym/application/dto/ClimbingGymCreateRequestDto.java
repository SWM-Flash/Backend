package com.first.flash.climbing.gym.application.dto;

import com.first.flash.climbing.gym.domian.ClimbingGym;
import com.first.flash.climbing.gym.domian.vo.Difficulty;
import java.util.List;

public record ClimbingGymCreateRequestDto(String gymName, String thumbnailUrl, String mapImageUrl, List<Difficulty> difficulties) {

    public ClimbingGym toEntity() {
        return new ClimbingGym(gymName, thumbnailUrl, mapImageUrl, difficulties);
    }
}
