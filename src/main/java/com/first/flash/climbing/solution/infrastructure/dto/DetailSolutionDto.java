package com.first.flash.climbing.solution.infrastructure.dto;

import com.first.flash.climbing.solution.domain.PerceivedDifficulty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record DetailSolutionDto(Long solutionId, String videoUrl, String gymName, String sectorName,
                                UUID problemId, String review, String difficultyName,
                                Long commentsCount, PerceivedDifficulty perceivedDifficulty,
                                String thumbnailImageUrl, String holdColorCode,
                                LocalDate solvedDate,
                                LocalDate removalDate, LocalDate settingDate,
                                LocalDateTime uploadedAt, boolean hasOtherSolutions) {

}
