package com.first.flash.climbing.sector.infrastructure.dto;

import com.first.flash.climbing.sector.domain.SectorInfo;

public record UpdateSectorsDto(String name, String adminName, String selectedImageUrl) {

    public static UpdateSectorsDto toDto(final SectorInfo sectorInfo) {
        return new UpdateSectorsDto(sectorInfo.getSectorName().getName(),
            sectorInfo.getSectorName().getAdminName(), sectorInfo.getSelectedImageUrl());
    }
}
