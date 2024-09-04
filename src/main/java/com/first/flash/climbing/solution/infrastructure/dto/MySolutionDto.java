package com.first.flash.climbing.solution.infrastructure.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record MySolutionDto(String gymName, String sectorName, String difficultyName,
                            UUID problemId, Long solutionId, LocalDateTime uploadedAt) {

}
