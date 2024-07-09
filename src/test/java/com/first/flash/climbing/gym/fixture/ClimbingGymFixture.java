package com.first.flash.climbing.gym.fixture;

import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateRequestDto;
import com.first.flash.climbing.gym.domian.ClimbingGym;
import com.first.flash.climbing.gym.domian.vo.Difficulty;
import java.util.List;

public class ClimbingGymFixture {

    public static ClimbingGym createDefaultGym() {
        return new ClimbingGym("test gym", "example.com", "example.com",
            List.of(new Difficulty("빨강", 1)));
    }

    public static ClimbingGymCreateRequestDto createDefaultGymCreateRequestDto() {
        return new ClimbingGymCreateRequestDto("test gym", "example.com", "example.com",
            List.of(new Difficulty("빨강", 1)));
    }
}
