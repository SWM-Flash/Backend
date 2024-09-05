package com.first.flash.climbing.solution.infrastructure.dto;

import java.time.LocalDateTime;

public record MySolutionDto(Long solutionId, String videoUrl, String gymName, String sectorName,
                            String review, String difficultyName, LocalDateTime removalDate,
                            LocalDateTime settingDate, LocalDateTime uploadedAt) {

}
