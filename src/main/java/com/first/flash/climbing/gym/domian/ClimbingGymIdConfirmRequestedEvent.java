package com.first.flash.climbing.gym.domian;

import com.first.flash.global.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClimbingGymIdConfirmRequestedEvent extends Event {

    private final Long gymId;

    public static ClimbingGymIdConfirmRequestedEvent of(final Long gymId) {
        return new ClimbingGymIdConfirmRequestedEvent(gymId);
    }
}
