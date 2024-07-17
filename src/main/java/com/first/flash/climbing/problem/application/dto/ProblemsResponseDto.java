package com.first.flash.climbing.problem.application.dto;

import com.first.flash.climbing.problem.domain.QueryProblem;
import java.util.List;

public record ProblemsResponseDto(List<ProblemResponseDto> problems, Meta meta) {

    public static ProblemsResponseDto of(
        final List<QueryProblem> queryProblems, final String nextCursor) {
        if (queryProblems.isEmpty()) {
            return new ProblemsResponseDto(List.of(), null);
        }
        return new ProblemsResponseDto(queryProblems.stream()
                                                    .map(ProblemResponseDto::toDto)
                                                    .toList(), Meta.from(nextCursor));
    }
}
