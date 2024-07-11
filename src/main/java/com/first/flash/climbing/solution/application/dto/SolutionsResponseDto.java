package com.first.flash.climbing.solution.application.dto;

import java.util.List;

public record SolutionsResponseDto(List<SolutionResponseDto> solutions, Meta meta) {

    public static record Meta(int count) {

    }
}
