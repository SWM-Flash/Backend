package com.first.flash.climbing.sector.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record SectorCreateRequestDto(
    @NotEmpty(message = "섹터 이름은 필수입니다.") String name,
    @NotEmpty(message = "섹터 관리 이름은 필수입니다.") String adminName,
    @NotNull(message = "세팅일 정보는 비어있을 수 없습니다.") LocalDate settingDate,
    LocalDate removalDate) {

}