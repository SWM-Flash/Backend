package com.first.flash.climbing.gym.application.dto;

import java.util.List;
import java.util.Set;

public record ClimbingGymDetailResponseDto(String mapImageUrl, List<String> difficulties,
                                           Set<String> sectors) {

}
