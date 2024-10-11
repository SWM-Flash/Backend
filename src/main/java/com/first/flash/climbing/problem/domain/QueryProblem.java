package com.first.flash.climbing.problem.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(
    indexes = {
        @Index(name = "idx_is_expired_views_id", columnList = "isExpired, views, id"),
        @Index(name = "idx_is_expired_difficulty_id",
            columnList = "isExpired, difficultyLevel, id"),
        @Index(name = "idx_is_expired_recommend_id",
            columnList = "isExpired, recommendationValue, id")
    }
)
@ToString
public class QueryProblem {

    private static final Boolean DEFAULT_HAS_SOLUTION = false;
    private static final int STANDARD_VIEW_COUNT = 100;
    private static final int DIFFICULTY_LEVEL_WEIGHT = 2;

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String imageUrl;
    private Integer views;
    private Boolean isExpired;
    private Integer solutionCount;
    @Setter
    private Integer perceivedDifficulty;
    private Long recommendationValue;
    private Boolean hasSolution;
    private Boolean isFakeRemovalDate;
    private String difficultyName;
    private Integer difficultyLevel;
    private Long optionalWeight;
    private String imageSource;
    private Long gymId;
    private String gymName;
    private Long sectorId;
    private String sectorName;
    private LocalDate settingDate;
    private LocalDate removalDate;

    public boolean isExpired() {
        return isExpired;
    }

    public void view() {
        views++;
        calculateRecommendationValue();
    }

    public void addSolutionCount() {
        solutionCount++;
        enableSolution();
        calculateRecommendationValue();
    }

    public void decrementSolutionCount() {
        solutionCount--;
        if (solutionCount == 0) {
            hasSolution = false;
        }
        calculateRecommendationValue();
    }

    public void addPerceivedDifficulty(final Integer value) {
        perceivedDifficulty += value;
    }

    public void subtractPerceivedDifficulty(final Integer value) {
        perceivedDifficulty -= value;
    }

    private void enableSolution() {
        if (!hasSolution) {
            hasSolution = true;
        }
    }

    private void calculateRecommendationValue() {
        if (views < STANDARD_VIEW_COUNT) {
            recommendationValue = ((long) views * solutionCount + optionalWeight);
            return;
        }
        recommendationValue =
            (STANDARD_VIEW_COUNT + difficultyLevel * DIFFICULTY_LEVEL_WEIGHT) * solutionCount
                + optionalWeight;
    }
}
