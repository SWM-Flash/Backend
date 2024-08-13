package com.first.flash.climbing.solution.ui;

import com.first.flash.climbing.solution.application.SolutionSaveService;
import com.first.flash.climbing.solution.application.SolutionService;
import com.first.flash.climbing.solution.application.dto.SolutionResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionsResponseDto;
import com.first.flash.climbing.solution.domain.dto.SolutionCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "solutions", description = "해설 관리 API")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class SolutionController {

    private final SolutionService solutionService;
    private final SolutionSaveService solutionSaveService;

    @Operation(summary = "해설 조회", description = "특정 문제에 대한 해설 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 해설을 조회함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SolutionsResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "문제를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "문제 없음", value = "{\"error\": \"아이디가 0190c558-9063-7050-b4fc-eb421e3236b3인 문제를 찾을 수 없습니다.\"}")
            }))
    })
    @GetMapping("problems/{problemId}/solutions")
    public ResponseEntity<SolutionsResponseDto> getSolutions(@PathVariable final UUID problemId) {
        SolutionsResponseDto response = solutionService.findAllSolutionsByProblemId(
            problemId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "해설 업로드", description = "특정 문제에 대한 해설 업로드")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 해설을 업로드함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SolutionResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 형식",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "요청값 누락", value = "{\"videoUrl\": \"비디오 URL은 필수입니다.\"}"),
            })),
        @ApiResponse(responseCode = "404", description = "문제를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "문제 없음", value = "{\"error\": \"아이디가 0190c558-9063-7050-b4fc-eb421e3236b3인 문제를 찾을 수 없습니다.\"}")
            }))
    })
    @PostMapping("problems/{problemId}/solutions")
    public ResponseEntity<SolutionResponseDto> createSolution(@PathVariable final UUID problemId,
        @Valid @RequestBody final SolutionCreateRequestDto solutionCreateRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(
                                 solutionSaveService.saveSolution(problemId,
                                     solutionCreateRequestDto));
    }
}
