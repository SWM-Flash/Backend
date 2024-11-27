package com.first.flash.climbing.sector.application.dto;

import java.util.List;

public record SectorInfosDetailResponseDto(List<SectorInfoDetailResponseDto> sectorInfosResponse) {

    public static SectorInfosDetailResponseDto toDto(
        final List<SectorInfoDetailResponseDto> sectorInfosResponse) {
        return new SectorInfosDetailResponseDto(sectorInfosResponse);
    }
}
