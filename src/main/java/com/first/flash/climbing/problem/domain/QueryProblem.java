package com.first.flash.climbing.problem.domain;

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

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(
    indexes = {
        @Index(name = "idx_is_expired_views_id", columnList = "isExpired, views, id"),
        @Index(name = "idx_is_expired_difficulty_id",
            columnList = "isExpired, difficultyLevel, id")
    }
)
public class QueryProblem {

    private static final Boolean DEFAULT_HAS_SOLUTION = false;

    @Id
    private UUID id;
    private String imageUrl;
    private Integer views;
    private Boolean isExpired;
    private Boolean hasSolution;
    private Boolean isFakeRemovalDate;
    private String difficultyName;
    private Integer difficultyLevel;
    private Long optionalWeight;
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
    }
}
