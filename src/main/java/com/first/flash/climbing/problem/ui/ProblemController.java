package com.first.flash.climbing.problem.ui;

import com.first.flash.climbing.problem.application.ProblemReadService;
import com.first.flash.climbing.problem.application.ProblemsSaveService;
import com.first.flash.climbing.problem.application.dto.ProblemCreateResponseDto;
import com.first.flash.climbing.problem.application.dto.ProblemDetailResponseDto;
import com.first.flash.climbing.problem.application.dto.ProblemsResponseDto;
import com.first.flash.climbing.problem.domain.dto.ProblemCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private final static String DEFAULT_SORT_BY = "recommend";

    private final ProblemsSaveService problemsSaveService;
    private final ProblemReadService problemReadService;

    @Operation(summary = "문제 저장", description = "특정 섹터에 문제 저장")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 문제 저장함"),
    })
    @PostMapping("/gyms/{gymId}/sectors/{sectorId}/problems")
    public ResponseEntity<ProblemCreateResponseDto> saveProblems(
        @PathVariable("gymId") final Long gymId,
        @PathVariable("sectorId") final Long sectorId,
        @RequestBody final ProblemCreateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(problemsSaveService.saveProblems(gymId, sectorId, requestDto));
    }

    @Operation(summary = "문제 여러건 조회", description = "문제 필터링, 정렬, 페이지네이션 후 여러건 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 문제 조회"),
    })
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

    @Operation(summary = "문제 단건 조회", description = "특정 문제의 정보 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 문제 정보 조회함"),
    })
    @GetMapping("/problems/{problemId}")
    public ResponseEntity<ProblemDetailResponseDto> findProblemById(
        @PathVariable final UUID problemId) {
        return ResponseEntity.ok(problemReadService.viewProblems(problemId));
    }
}
