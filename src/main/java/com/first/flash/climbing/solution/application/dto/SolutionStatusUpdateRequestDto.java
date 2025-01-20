package com.first.flash.climbing.solution.application.dto;

import com.first.flash.climbing.solution.domain.vo.SolutionVideoStatus;
import com.first.flash.global.annotation.ValidEnum;

public record SolutionStatusUpdateRequestDto(
    @ValidEnum(enumClass = SolutionVideoStatus.class) SolutionVideoStatus status, String videoUrl) {

}
