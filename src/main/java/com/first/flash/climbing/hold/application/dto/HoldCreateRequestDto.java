package com.first.flash.climbing.hold.application.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record HoldCreateRequestDto(
    @NotNull(message = "홀드 색상 이름 정보는 비어있을 수 없습니다.") String colorName,
    @NotNull(message = "홀드 색상 코드 정보는 비어있을 수 없습니다.") String colorCode) {

}
