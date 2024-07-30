package com.first.flash.climbing.sector.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SectorCreatedEvent {

    private Long gymId;

    public static SectorCreatedEvent of(final Long gymId) {
        return new SectorCreatedEvent(gymId);
    }
}
