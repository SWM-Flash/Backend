package com.first.flash.climbing.problem.domain;

import com.first.flash.climbing.problem.domain.vo.DifficultyInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Problem {

    private static final Long DEFAULT_OPTIONAL_WEIGHT = 0L;
    private static final Integer DEFAULT_VIEWS = 0;
    private static final Boolean DEFAULT_IS_EXPIRED = false;

    @Id
    private UUID id;
    private String imageUrl;
    private Integer views;
    private Boolean isExpired;
    private DifficultyInfo difficultyInfo;
    private Long optionalWeight;
    private Long gymId;
    private Long sectorId;

    public static Problem createDefault(final UUID id, final String imageUrl,
        final String difficultyName, final Integer difficultyLevel, final Long gymId,
        final Long sectorId) {
        return Problem.builder()
                      .id(id)
                      .imageUrl(imageUrl)
                      .views(DEFAULT_VIEWS)
                      .isExpired(DEFAULT_IS_EXPIRED)
                      .difficultyInfo(DifficultyInfo.of(difficultyName, difficultyLevel))
                      .optionalWeight(DEFAULT_OPTIONAL_WEIGHT)
                      .gymId(gymId)
                      .sectorId(sectorId)
                      .build();
    }

    public void view() {
        views++;
    }

    public boolean isExpired() {
        return isExpired;
    }
}
