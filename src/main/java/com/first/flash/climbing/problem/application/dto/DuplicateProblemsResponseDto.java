package com.first.flash.climbing.problem.application.dto;

import com.first.flash.climbing.problem.domain.QueryProblem;
import com.first.flash.global.paging.Meta;
import java.util.List;

public record DuplicateProblemsResponseDto(List<ProblemResponseDto> problems) {

    public static DuplicateProblemsResponseDto of(final List<QueryProblem> queryProblems) {
        return new DuplicateProblemsResponseDto(queryProblems.stream()
                                                    .map(ProblemResponseDto::toDto)
                                                    .toList());
    }
}
