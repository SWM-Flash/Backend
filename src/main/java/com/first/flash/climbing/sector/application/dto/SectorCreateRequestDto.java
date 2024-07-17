package com.first.flash.climbing.sector.application.dto;

import java.time.LocalDate;

public record SectorCreateRequestDto(String name, String adminName,
                                     LocalDate settingDate, LocalDate removalDate) {

}
