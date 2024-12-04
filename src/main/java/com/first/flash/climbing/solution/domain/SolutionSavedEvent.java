package com.first.flash.climbing.solution.domain;

import com.first.flash.global.event.Event;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SolutionSavedEvent extends Event {

    private UUID problemId;
    private Long solutionId;
    private String thumbnailImageUrl;
    private String uploader;

    public static SolutionSavedEvent of(final UUID problemId, Long solutionId,
        String thumbnailImageUrl, String uploader) {
        return new SolutionSavedEvent(problemId, solutionId, thumbnailImageUrl, uploader);
    }
}
