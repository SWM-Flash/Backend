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
    private Integer perceivedDifficulty;

    protected SolutionDetail(final String review, final String videoUrl, final Integer perceivedDifficulty) {
        this.review = review;
        this.videoUrl = videoUrl;
        this.perceivedDifficulty = perceivedDifficulty;
    }

    public static SolutionDetail of(final String review, final String videoUrl, final Integer perceivedDifficulty) {
        return new SolutionDetail(review, videoUrl, perceivedDifficulty);
    }
}
