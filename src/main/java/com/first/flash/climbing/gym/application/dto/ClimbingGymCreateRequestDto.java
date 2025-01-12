package com.first.flash.climbing.gym.application.dto;

import com.first.flash.climbing.gym.domian.ClimbingGym;
import jakarta.validation.constraints.NotEmpty;

public record ClimbingGymCreateRequestDto(
    @NotEmpty(message = "클라이밍장 이름은 필수입니다.") String gymName,
    @NotEmpty(message = "썸네일 URL은 필수입니다.") String thumbnailUrl,
    String mapImageUrl,
    @NotEmpty(message = "일정 이미지 URL은 필수입니다.") String calendarImageUrl) {

    public ClimbingGym toEntity() {
        return new ClimbingGym(gymName, thumbnailUrl, mapImageUrl, calendarImageUrl);
    }
}
