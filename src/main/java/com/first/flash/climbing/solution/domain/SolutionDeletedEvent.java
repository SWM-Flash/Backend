package com.first.flash.climbing.solution.domain;

import com.first.flash.global.event.Event;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SolutionDeletedEvent extends Event {

    private UUID problemId;

    public static SolutionDeletedEvent of(final UUID problemId) {
        return new SolutionDeletedEvent(problemId);
    }

}