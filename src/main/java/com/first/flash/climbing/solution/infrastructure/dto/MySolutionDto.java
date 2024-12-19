package com.first.flash.climbing.solution.infrastructure.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MySolutionDto(Long solutionId, String gymName, String sectorName,
                            String difficultyName, String thumbnailImageUrl, Long commentsCount,
                            LocalDate solvedDate, LocalDateTime uploadedAt) {

}
