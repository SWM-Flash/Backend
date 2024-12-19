package com.first.flash.climbing.solution.domain.vo;

import com.first.flash.climbing.solution.domain.PerceivedDifficulty;
import com.first.flash.climbing.solution.domain.PerceivedDifficultyConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
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
    private String thumbnailImageUrl;
    private String videoUrl;
    private LocalDate solvedDate;
    @Convert(converter = PerceivedDifficultyConverter.class)
    private PerceivedDifficulty perceivedDifficulty;

    protected SolutionDetail(final String review, final String thumbnailImageUrl, final String videoUrl,
        final LocalDate solvedDate, final PerceivedDifficulty perceivedDifficulty) {
        this.review = review;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.videoUrl = videoUrl;
        this.solvedDate = solvedDate;
        this.perceivedDifficulty = perceivedDifficulty;
    }

    public static SolutionDetail of(final String review, final String thumbnailImageUrl, final String videoUrl,
        final LocalDate solvedDate, final PerceivedDifficulty perceivedDifficulty) {
        return new SolutionDetail(review, thumbnailImageUrl, videoUrl, solvedDate, perceivedDifficulty);
    }
}
