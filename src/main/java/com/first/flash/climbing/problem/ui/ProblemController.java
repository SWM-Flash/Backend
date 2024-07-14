package com.first.flash.climbing.problem.ui;

import com.first.flash.climbing.problem.application.ProblemReadService;
import com.first.flash.climbing.problem.application.ProblemsSaveService;
import com.first.flash.climbing.problem.application.dto.ProblemCreateResponseDto;
import com.first.flash.climbing.problem.application.dto.ProblemDetailResponseDto;
import com.first.flash.climbing.problem.application.dto.ProblemsResponseDto;
import com.first.flash.climbing.problem.domain.dto.ProblemCreateRequestDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProblemController {

    private final static String DEFAULT_SIZE = "2";
    private final static String DEFAULT_SORT_BY = "view";

    private final ProblemsSaveService problemsSaveService;
    private final ProblemReadService problemReadService;

    @PostMapping("/gyms/{gymId}/sectors/{sectorId}/problems")
    public ResponseEntity<ProblemCreateResponseDto> saveProblems(
        @PathVariable("gymId") final Long gymId,
        @PathVariable("sectorId") final Long sectorId,
        @RequestBody final ProblemCreateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(problemsSaveService.saveProblems(gymId, sectorId, requestDto));
    }

    @GetMapping("/gyms/{gymId}/problems")
    public ResponseEntity<ProblemsResponseDto> findAllProblems(
        @PathVariable final Long gymId,
        @RequestParam(name = "cursor", required = false) final String cursor,
        @RequestParam(defaultValue = DEFAULT_SORT_BY, required = false) final String sortBy,
        @RequestParam(defaultValue = DEFAULT_SIZE, required = false) final int size,
        @RequestParam(required = false) final List<String> difficulty,
        @RequestParam(required = false) final List<String> sector,
        @RequestParam(name = "has-solution", required = false) final Boolean hasSolution) {
        return ResponseEntity.ok(
            problemReadService.findAll(gymId, cursor, sortBy, size, difficulty, sector,
                hasSolution));
    }

    @GetMapping("/problems/{problemId}")
    public ResponseEntity<ProblemDetailResponseDto> findProblemById(
        @PathVariable final UUID problemId) {
        return ResponseEntity.ok(problemReadService.viewProblems(problemId));
    }
}
