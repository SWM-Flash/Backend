package com.first.flash.climbing.gym.application;

import com.first.flash.climbing.gym.domian.ClimbingGymIdConfirmRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ClimbingGymEventHandler {

    private final ClimbingGymService gymService;

    @EventListener
    @Transactional
    public void confirmGymId(final ClimbingGymIdConfirmRequestedEvent event) {
        gymService.findClimbingGymById(event.getGymId());
    }
}
