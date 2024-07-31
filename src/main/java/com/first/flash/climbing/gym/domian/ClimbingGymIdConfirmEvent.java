package com.first.flash.climbing.gym.domian;

import com.first.flash.global.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClimbingGymIdConfirmEvent extends Event {

    private final Long gymId;

    public static ClimbingGymIdConfirmEvent of(final Long gymId) {
        return new ClimbingGymIdConfirmEvent(gymId);
    }
}
