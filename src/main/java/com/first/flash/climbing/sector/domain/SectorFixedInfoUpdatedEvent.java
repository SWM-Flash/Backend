package com.first.flash.climbing.sector.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SectorFixedInfoUpdatedEvent {

    private List<Long> sectorIds;
    private String sectorName;

    public static SectorFixedInfoUpdatedEvent of(final List<Long> sectorIds,
        final SectorInfo sectorInfo) {
        return new SectorFixedInfoUpdatedEvent(sectorIds, sectorInfo.getSectorName().getName());
    }
}
