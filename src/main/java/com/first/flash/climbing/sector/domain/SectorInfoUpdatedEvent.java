package com.first.flash.climbing.sector.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SectorInfoUpdatedEvent {

    private Long id;
    private String sectorName;
    private LocalDate settingDate;
    private Long gymId;

    public static SectorInfoUpdatedEvent of(final Long id, final String sectorName,
        final LocalDate settingDate, final Long gymId) {
        return new SectorInfoUpdatedEvent(id, sectorName, settingDate, gymId);
    }
}
