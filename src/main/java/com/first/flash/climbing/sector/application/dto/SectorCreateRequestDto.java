package com.first.flash.climbing.sector.application.dto;

import com.first.flash.climbing.sector.domain.Sector;
import java.time.LocalDate;
import java.util.Objects;

public record SectorCreateRequestDto(String name, String adminName,
                                     LocalDate settingDate, LocalDate removalDate, Long gymId) {

    public Sector toEntity() {
        if (Objects.isNull(removalDate)) {
            return Sector.createExceptRemovalDate(name, adminName, settingDate, gymId);

        }
        return Sector.createDefault(name, adminName, settingDate, removalDate, gymId);
    }
}
