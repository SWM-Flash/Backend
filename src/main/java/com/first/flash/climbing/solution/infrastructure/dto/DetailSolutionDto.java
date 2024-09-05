package com.first.flash.climbing.solution.infrastructure.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DetailSolutionDto(Long solutionId, String videoUrl, String gymName, String sectorName,
                                String review, String difficultyName, LocalDate removalDate,
                                LocalDate settingDate, LocalDateTime uploadedAt) {

}
