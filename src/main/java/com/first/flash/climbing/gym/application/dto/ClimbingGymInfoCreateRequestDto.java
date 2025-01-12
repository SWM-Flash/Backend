package com.first.flash.climbing.gym.application.dto;

import com.first.flash.climbing.gym.domian.ClimbingGymInfo;
import com.first.flash.climbing.gym.domian.vo.Difficulty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ClimbingGymInfoCreateRequestDto(
    @NotEmpty(message = "클라이밍장 이름은 필수입니다.") String gymName,
    @NotEmpty(message = "난이도 정보는 최소 하나 이상이어야 합니다.")
    List<@Valid @NotNull(message = "난이도 정보는 비어있을 수 없습니다.") Difficulty> difficulties) {

    public ClimbingGymInfo toEntity() {
        return new ClimbingGymInfo(gymName, difficulties);
    }
}
