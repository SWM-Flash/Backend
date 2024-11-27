package com.first.flash.climbing.sector.application.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record SectorCreateRequestDto(
    @NotNull(message = "세팅일 정보는 비어있을 수 없습니다.") LocalDate settingDate,
    LocalDate removalDate) {

}
