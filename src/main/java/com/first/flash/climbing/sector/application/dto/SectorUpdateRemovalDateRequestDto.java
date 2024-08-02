package com.first.flash.climbing.sector.application.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record SectorUpdateRemovalDateRequestDto(
    @NotNull(message = "탈거일 수정 시 탈거일 정보는 비어있을 수 없습니다.") LocalDate removalDate) {

}
