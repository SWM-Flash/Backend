package com.first.flash.climbing.solution.ui;

import com.first.flash.climbing.solution.application.SolutionService;
import com.first.flash.climbing.solution.application.dto.SolutionResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionsResponseDto;
import com.first.flash.climbing.solution.domain.dto.SolutionCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "solutions", description = "해설 영상 관리 API")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class SolutionController {

    private final SolutionService solutionService;

    @Operation(summary = "해설 영상 조회", description = "특정 문제에 대한 해설 영상 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 해설 영상을 조회함")
    })
    @GetMapping("problems/{problemId}/solutions")
    public ResponseEntity<SolutionsResponseDto> getSolutions(@PathVariable final Long problemId) {
        SolutionsResponseDto response = solutionService.findAllSolutionsByProblemId(
            problemId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "해설 영상 업로드", description = "특정 문제에 대한 해설 영상 업로드")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 해설 영상을 업로드함")
    })
    @PostMapping("problems/{problemId}/solutions")
    public ResponseEntity<SolutionResponseDto> createSolution(@PathVariable final Long problemId,
        @RequestBody final SolutionCreateRequestDto solutionCreateRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(solutionService.saveSolution(problemId, solutionCreateRequestDto));
    }
}
