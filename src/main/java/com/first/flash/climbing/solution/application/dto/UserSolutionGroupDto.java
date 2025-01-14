package com.first.flash.climbing.solution.application.dto;

import java.time.LocalDate;
import java.util.List;

public record UserSolutionGroupDto(String gymName, List<DifficultyDto> difficulties,
                                   LocalDate solvedDate, String thumbnailImageUrl) {

}
