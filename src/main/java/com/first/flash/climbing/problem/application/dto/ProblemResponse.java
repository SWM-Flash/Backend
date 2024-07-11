package com.first.flash.climbing.problem.application.dto;

import com.first.flash.climbing.problem.domain.QueryProblem;
import java.time.LocalDate;
import java.util.UUID;

public record ProblemResponse(UUID id, String sector, String difficulty, LocalDate settingDate,
                              boolean hasSolution, String imageUrl) {

    public static ProblemResponse toDto(QueryProblem queryProblem) {
        return new ProblemResponse(queryProblem.getId(), queryProblem.getSectorName(),
            queryProblem.getDifficultyName(), queryProblem.getSettingDate(),
            queryProblem.getHasSolution(), queryProblem.getImageUrl());
    }
}
