package com.first.flash.climbing.problem.application.dto;

import com.first.flash.climbing.problem.domain.QueryProblem;
import java.time.LocalDate;
import java.util.UUID;

public record ProblemResponseDto(UUID id, String sector, String difficulty, LocalDate settingDate,
                                 LocalDate removalDate, boolean hasSolution, String imageUrl,
                                 String holdColorCode, Boolean isHoney, Integer solutionCount) {

    public static ProblemResponseDto toDto(QueryProblem queryProblem) {
        return new ProblemResponseDto(queryProblem.getId(), queryProblem.getSectorName(),
            queryProblem.getDifficultyName(), queryProblem.getSettingDate(),
            queryProblem.getRemovalDate(), queryProblem.getHasSolution(),
            queryProblem.getImageUrl(), queryProblem.getHoldColorCode(),
            queryProblem.isHoney(), queryProblem.getSolutionCount());
    }
}
