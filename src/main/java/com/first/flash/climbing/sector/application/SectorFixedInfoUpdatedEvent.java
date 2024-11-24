package com.first.flash.climbing.sector.application;

import com.first.flash.climbing.sector.domain.SectorInfo;
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
