package com.first.flash.climbing.solution.application.dto;

import com.first.flash.climbing.solution.domain.dto.SolutionResponseDto;
import java.util.List;

public record SolutionsResponseDto(List<SolutionResponseDto> solutions,
                                   SolutionMetaResponseDto meta) {

    public static SolutionsResponseDto of(final List<SolutionResponseDto> solutions) {
        return new SolutionsResponseDto(solutions,
            new SolutionMetaResponseDto(solutions.size()));
    }
}
