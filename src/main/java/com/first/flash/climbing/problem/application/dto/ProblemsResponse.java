package com.first.flash.climbing.problem.application.dto;

import com.first.flash.climbing.problem.domain.QueryProblem;
import java.util.List;

public record ProblemsResponse(List<ProblemResponse> problems, Meta meta) {

    public static ProblemsResponse of(
        final List<QueryProblem> queryProblems, final String nextCursor) {
        if (queryProblems.isEmpty()) {
            return new ProblemsResponse(List.of(), null);
        }
        return new ProblemsResponse(queryProblems.stream()
                                                 .map(ProblemResponse::toDto)
                                                 .toList(), Meta.from(nextCursor));
    }
}
