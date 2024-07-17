package com.first.flash.climbing.solution.domain;

import com.first.flash.global.event.Event;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SolutionSavedEvent extends Event {

    private UUID problemId;

    public static SolutionSavedEvent of(final UUID problemId) {
        return new SolutionSavedEvent(problemId);
    }
}
