package com.first.flash.climbing.sector.application.dto;

import com.first.flash.climbing.sector.domain.Sector;
import com.first.flash.climbing.sector.domain.vo.RemovalInfo;
import com.first.flash.climbing.sector.domain.vo.SectorName;
import java.time.LocalDate;

public record SectorCreateResponseDto(Long id, SectorName sectorName, LocalDate settingDate,
                                      RemovalInfo removalInfo, Long gymId) {

    public static SectorCreateResponseDto toDto(final Sector sector) {
        return new SectorCreateResponseDto(sector.getId(), sector.getSectorName(),
            sector.getSettingDate(), sector.getRemovalInfo(), sector.getGymId());
    }
}
