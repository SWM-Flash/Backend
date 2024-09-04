package com.first.flash.climbing.solution.application.dto;

import com.first.flash.climbing.solution.infrastructure.dto.MySolutionDto;
import java.util.List;

public record MySolutionsResponseDto(List<MySolutionDto> mySolutions,
                                     MySolutionMetaResponseDto meta) {

    public static MySolutionsResponseDto of(final List<MySolutionDto> mySolutions) {
        return new MySolutionsResponseDto(mySolutions,
            new MySolutionMetaResponseDto(mySolutions.size()));
    }
}
