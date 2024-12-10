package com.first.flash.climbing.solution.infrastructure.dto;

import com.first.flash.climbing.solution.domain.PerceivedDifficulty;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record DetailSolutionDto(Long solutionId, String videoUrl, String gymName, String sectorName,
                                String review, String difficultyName, Long commentsCount, PerceivedDifficulty perceivedDifficulty,
                                String thumbnailImageUrl, String holdColorCode,
                                LocalDate removalDate, LocalDate settingDate, LocalDateTime uploadedAt) {
}
