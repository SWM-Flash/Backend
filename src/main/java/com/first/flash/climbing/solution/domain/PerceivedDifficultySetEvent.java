package com.first.flash.climbing.solution.domain;

import com.first.flash.global.event.Event;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PerceivedDifficultySetEvent extends Event {

    private UUID problemId;
    private Integer perceivedDifficulty;

    public static PerceivedDifficultySetEvent of(final UUID problemId, final Integer perceivedDifficulty) {
        return new PerceivedDifficultySetEvent(problemId, perceivedDifficulty);
    }
}
