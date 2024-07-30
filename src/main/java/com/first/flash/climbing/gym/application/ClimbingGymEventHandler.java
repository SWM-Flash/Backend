package com.first.flash.climbing.gym.application;

import com.first.flash.climbing.sector.domain.SectorCreatedEvent;
import com.first.flash.climbing.sector.domain.SectorInfoUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClimbingGymEventHandler {

    private final ClimbingGymService gymService;

    @EventListener
    public void handleSectorCreatedEvent(final SectorCreatedEvent event) {
        validateGymId(event.getGymId());
    }

    @EventListener
    public void handleSectorInfoUpdatedEvent(final SectorInfoUpdatedEvent event) {
        validateGymId(event.getGymId());
    }

    private void validateGymId(Long gymId) {
        gymService.findClimbingGymById(gymId);
    }
}
