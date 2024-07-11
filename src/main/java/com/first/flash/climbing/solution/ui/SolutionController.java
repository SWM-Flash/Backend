package com.first.flash.climbing.solution.ui;

import com.first.flash.climbing.solution.application.SolutionService;
import com.first.flash.climbing.solution.domain.dto.SolutionCreateRequestDto;
import com.first.flash.climbing.solution.domain.dto.SolutionResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SolutionController {

    private final SolutionService solutionService;

    @GetMapping("problems/{problemId}/solutions")
    public ResponseEntity<List<SolutionResponseDto>> getSolutions(@PathVariable Long problemId) {
        List<SolutionResponseDto> response = solutionService.findAllSolutionsByProblemId(
            problemId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("problems/{problemId}/solutions")
    public ResponseEntity<SolutionResponseDto> createSolution(@PathVariable final Long problemId,
        @RequestBody final SolutionCreateRequestDto solutionCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(solutionService.saveSolution(problemId, solutionCreateRequestDto));
    }
}
