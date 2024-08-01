package com.first.flash.climbing.problem.domain;

import com.first.flash.global.event.Event;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProblemIdConfirmRequestedEvent extends Event {

    private final UUID problemId;

    public static ProblemIdConfirmRequestedEvent of(final UUID problemId) {
        return new ProblemIdConfirmRequestedEvent(problemId);
    }
}
