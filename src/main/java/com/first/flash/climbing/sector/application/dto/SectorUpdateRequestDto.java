package com.first.flash.climbing.sector.application.dto;

import java.time.LocalDate;

public record SectorUpdateRequestDto(String sectorName, String adminSectorName, LocalDate settingDate,
                                     LocalDate removalDate, Long gymId) {

    public static SectorUpdateRequestDto of(final String sectorName, final String adminSectorName,
        final LocalDate settingDate, final LocalDate removalDate, final Long gymId) {
        return new SectorUpdateRequestDto(sectorName, adminSectorName, settingDate, removalDate,
            gymId);
    }
}
