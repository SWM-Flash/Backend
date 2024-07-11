package com.first.flash.climbing.problem.ui;

import com.first.flash.climbing.problem.application.ProblemsSaveService;
import com.first.flash.climbing.problem.application.dto.ProblemCreateResponseDto;
import com.first.flash.climbing.problem.domain.dto.ProblemCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemsSaveService problemsSaveService;

    @PostMapping("/gyms/{gymId}/sectors/{sectorId}/problems")
    public ResponseEntity<ProblemCreateResponseDto> saveProblems(
        @PathVariable("gymId") final Long gymId,
        @PathVariable("sectorId") final Long sectorId,
        @RequestBody ProblemCreateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(problemsSaveService.saveProblems(gymId, sectorId, requestDto));
    }
}
