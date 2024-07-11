package com.first.flash.climbing.problem.application.dto;

import com.first.flash.climbing.problem.domain.QueryProblem;
import java.util.List;

public record ProblemsResponse(List<ProblemResponse> problems, CursorInfo meta) {

    public static ProblemsResponse of(final List<QueryProblem> queryProblems) {
        if (queryProblems.isEmpty()) {
            return new ProblemsResponse(List.of(), null);
        }

        return new ProblemsResponse(queryProblems.stream()
                                                 .map(ProblemResponse::toDto)
                                                 .toList(),
            new CursorInfo(queryProblems.get(queryProblems.size() - 1).getId()));
    }
}
