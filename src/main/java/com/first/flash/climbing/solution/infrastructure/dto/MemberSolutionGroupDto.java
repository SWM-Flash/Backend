package com.first.flash.climbing.solution.infrastructure.dto;

import java.time.LocalDate;
import java.util.List;

public record MemberSolutionGroupDto(Long gymId, String gymName, List<DifficultyDto> difficulties,
                                     LocalDate solvedDate, String thumbnailImageUrl) {

}
