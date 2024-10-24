package com.first.flash.climbing.gym.application.dto;

import com.first.flash.climbing.gym.domian.ClimbingGym;
import com.first.flash.climbing.gym.domian.vo.Difficulty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ClimbingGymCreateRequestDto(
    @NotEmpty(message = "클라이밍장 이름은 필수입니다.") String gymName,
    @NotEmpty(message = "썸네일 URL은 필수입니다.") String thumbnailUrl,
    String mapImageUrl,
    @NotEmpty(message = "일정 이미지 URL은 필수입니다.") String calendarImageUrl,
    @NotEmpty(message = "난이도 정보는 최소 하나 이상이어야 합니다.")
    List<@Valid @NotNull(message = "난이도 정보는 비어있을 수 없습니다.") Difficulty> difficulties) {

    public ClimbingGym toEntity() {
        return new ClimbingGym(gymName, thumbnailUrl, mapImageUrl, calendarImageUrl, difficulties);
    }
}
