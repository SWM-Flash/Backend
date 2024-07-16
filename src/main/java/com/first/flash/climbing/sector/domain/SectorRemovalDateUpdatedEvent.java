package com.first.flash.climbing.sector.domain;

import com.first.flash.global.event.Event;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SectorRemovalDateUpdatedEvent extends Event {

    private Long sectorId;
    private LocalDate removalDate;

    public static SectorRemovalDateUpdatedEvent of(
        final Long sectorId, final LocalDate removalDate) {
        return new SectorRemovalDateUpdatedEvent(sectorId, removalDate);
    }
}
