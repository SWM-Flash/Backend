package com.first.flash.climbing.solution.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class SolutionDetail {

    private String review;
    private String videoUrl;

    protected SolutionDetail(final String review, final String videoUrl) {
        this.review = review;
        this.videoUrl = videoUrl;
    }

    public static SolutionDetail of(final String review, final String videoUrl) {
        return new SolutionDetail(review, videoUrl);
    }
}
