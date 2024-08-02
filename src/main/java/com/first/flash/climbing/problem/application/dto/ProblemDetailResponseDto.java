package com.first.flash.climbing.problem.application.dto;

import com.first.flash.climbing.problem.domain.QueryProblem;
import java.time.LocalDate;
import java.util.UUID;

public record ProblemDetailResponseDto(UUID id, String sector, String difficulty,
                                       LocalDate settingDate, LocalDate removalDate,
                                       boolean isFakeRemovalDate, boolean hasSolution,
                                       String imageUrl, String gymName) {

    public static ProblemDetailResponseDto of(final QueryProblem queryProblem) {
        return new ProblemDetailResponseDto(queryProblem.getId(), queryProblem.getSectorName(),
            queryProblem.getDifficultyName(), queryProblem.getSettingDate(),
            queryProblem.getRemovalDate(), queryProblem.getIsFakeRemovalDate(),
            queryProblem.getHasSolution(), queryProblem.getImageUrl(), queryProblem.getGymName());
    }
}
