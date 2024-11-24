package com.first.flash.climbing.sector.application.dto;

import jakarta.validation.constraints.NotEmpty;

public record SectorInfoCreateRequestDto(@NotEmpty(message = "섹터 이름은 필수입니다.") String name,
                                         @NotEmpty(message = "섹터 관리 이름은 필수입니다.") String adminName,
                                         String selectedImageUrl) {

}
