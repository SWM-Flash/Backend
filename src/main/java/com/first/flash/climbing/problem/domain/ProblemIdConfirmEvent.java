package com.first.flash.climbing.problem.domain;

import com.first.flash.global.event.Event;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProblemIdConfirmEvent extends Event {

    private final UUID problemId;

    public static ProblemIdConfirmEvent of(final UUID problemId) {
        return new ProblemIdConfirmEvent(problemId);
    }
}
