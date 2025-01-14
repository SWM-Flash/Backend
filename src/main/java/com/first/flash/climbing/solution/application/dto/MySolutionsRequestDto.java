package com.first.flash.climbing.solution.application.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record MySolutionsRequestDto(@NotNull(message = "클라이밍장 id는 필수입니다.") Long gymId,
                                    @NotNull(message = "풀이 일자 정보는 필수입니다.") LocalDate solvedDate) {

}
