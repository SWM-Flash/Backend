package com.first.flash.climbing.solution.application.dto;

import com.first.flash.global.paging.Meta;
import java.util.List;

public record SolutionsPageResponseDto(List<UserSolutionGroupDto> mySolutions, Meta meta) {

    public static SolutionsPageResponseDto of(
        final List<UserSolutionGroupDto> solutions, final String nextCursor) {
        if (solutions.isEmpty()) {
            return new SolutionsPageResponseDto(List.of(), null);
        }
        return new SolutionsPageResponseDto(solutions, Meta.from(nextCursor));
    }
}
