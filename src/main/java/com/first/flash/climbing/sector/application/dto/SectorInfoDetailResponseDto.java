package com.first.flash.climbing.sector.application.dto;

import com.first.flash.climbing.sector.domain.SectorInfo;
import com.first.flash.climbing.sector.domain.vo.SectorName;

public record SectorInfoDetailResponseDto(Long id, SectorName sectorName, String selectedImageUrl,
                                          Long gymId) {

    public static SectorInfoDetailResponseDto toDto(final SectorInfo sectorInfo) {
        return new SectorInfoDetailResponseDto(sectorInfo.getId(), sectorInfo.getSectorName(),
            sectorInfo.getSelectedImageUrl(), sectorInfo.getGymId());
    }
}
