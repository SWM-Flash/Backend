package com.first.flash.climbing.gym.application.dto;

import com.first.flash.climbing.gym.infrastructure.dto.SectorInfoResponseDto;
import java.util.List;

public record ClimbingGymDetailResponseDto(String gymName, String mapImageUrl,
                                           String calendarImageUrl,
                                           List<String> difficulties,
                                           List<SectorInfoResponseDto> sectors) {

}
