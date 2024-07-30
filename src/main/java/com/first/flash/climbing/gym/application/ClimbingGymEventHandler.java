package com.first.flash.climbing.gym.application;

import com.first.flash.climbing.sector.domain.SectorCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClimbingGymEventHandler {

    private final ClimbingGymService gymService;

    @EventListener
    public void validGymId(final SectorCreatedEvent event) {
        gymService.findClimbingGymById(event.getGymId());
    }

}
