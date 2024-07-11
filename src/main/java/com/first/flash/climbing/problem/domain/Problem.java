package com.first.flash.climbing.problem.domain;

import com.first.flash.climbing.problem.domain.vo.DifficultyInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Problem {

    private static final Long DEFAULT_OPTIONAL_WEIGHT = 0L;
    private static final Integer DEFAULT_VIEWS = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private Integer views;
    private Boolean isExpired;
    private DifficultyInfo difficultyInfo;
    private Long optionalWeight;
    private Long gymId;
    private Long sectorId;

    public Problem(final String imageUrl, final Integer views, final Boolean isExpired,
        final DifficultyInfo difficultyInfo, final Long optionalWeight, final Long gymId,
        final Long sectorId) {
        this.imageUrl = imageUrl;
        this.views = views;
        this.isExpired = isExpired;
        this.difficultyInfo = difficultyInfo;
        this.optionalWeight = optionalWeight;
        this.gymId = gymId;
        this.sectorId = sectorId;
    }

    public static Problem createDefault(final String imageUrl, final Boolean isExpired,
        final String difficultyName, final Integer difficultyLevel, final Long gymId,
        final Long sectorId) {
        return new Problem(imageUrl, DEFAULT_VIEWS, isExpired,
            DifficultyInfo.of(difficultyName, difficultyLevel), DEFAULT_OPTIONAL_WEIGHT, gymId,
            sectorId);
    }

    public void view() {
        views++;
    }
}
