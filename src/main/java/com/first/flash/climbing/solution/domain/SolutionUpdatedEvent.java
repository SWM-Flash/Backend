package com.first.flash.climbing.solution.domain;

import com.first.flash.global.event.Event;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SolutionUpdatedEvent extends Event {

    private Long solutionId;
    private String thumbnailImageUrl;
    private String uploader;

    public static SolutionUpdatedEvent of(final Long solutionId, final String thumbnailImageUrl,
        final String uploader) {
        return new SolutionUpdatedEvent(solutionId, thumbnailImageUrl, uploader);
    }
}
