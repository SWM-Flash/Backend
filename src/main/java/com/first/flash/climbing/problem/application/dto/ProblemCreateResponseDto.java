package com.first.flash.climbing.problem.application.dto;

import com.first.flash.climbing.problem.domain.Problem;
import com.first.flash.climbing.problem.domain.vo.DifficultyInfo;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ProblemCreateResponseDto(UUID id, String imageUrl, Integer views, Boolean isExpired,
                                       DifficultyInfo difficultyInfo, Long optionalWeight,
                                       Long gymId, Long sectorId, String imageSource,
                                       Long thumbnailSolutionId, Long holdId
) {

    public static ProblemCreateResponseDto toDto(final Problem problem) {
        return ProblemCreateResponseDto.builder()
                                       .id(problem.getId())
                                       .imageUrl(problem.getImageUrl())
                                       .views(problem.getViews())
                                       .isExpired(problem.getIsExpired())
                                       .difficultyInfo(problem.getDifficultyInfo())
                                       .optionalWeight(problem.getOptionalWeight())
                                       .gymId(problem.getGymId())
                                       .sectorId(problem.getSectorId())
                                       .imageSource(problem.getImageSource())
                                       .thumbnailSolutionId(problem.getThumbnailSolutionId())
                                       .holdId(problem.getHoldId())
                                       .build();
    }
}
