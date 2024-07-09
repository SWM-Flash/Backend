package com.first.flash.climbing.sector.application.dto;

import com.first.flash.climbing.sector.domain.Sector;
import java.time.LocalDate;

public record SectorResponseDto(Long id, String name, LocalDate settingDate,
                                LocalDate removalDate, Long gymId) {

    public static SectorResponseDto toDto(final Sector sector) {
        return new SectorResponseDto(sector.getId(), sector.getDisplayName(),
            sector.getSettingDate(), sector.getRemovalDate(), sector.getGymId());
    }
}
