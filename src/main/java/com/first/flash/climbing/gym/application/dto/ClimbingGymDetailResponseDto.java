package com.first.flash.climbing.gym.application.dto;

import java.util.List;

public record ClimbingGymDetailResponseDto(String gymName, String mapImageUrl,
                                           List<String> difficulties,
                                           List<String> sectors) {

}
