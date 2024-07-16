package com.first.flash.climbing.sector.application;

import com.first.flash.global.event.Event;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SectorExpiredEvent extends Event {

    private List<Long> expiredSectorsIds;

    public static SectorExpiredEvent of(final List<Long> expiredSectorIds) {
        return new SectorExpiredEvent(expiredSectorIds);
    }
}
