package com.first.flash.climbing.problem.ui;

import com.first.flash.climbing.problem.application.ProblemReadService;
import com.first.flash.climbing.problem.application.ProblemsHoldService;
import com.first.flash.climbing.problem.application.ProblemsSaveService;
import com.first.flash.climbing.problem.application.ProblemsService;
import com.first.flash.climbing.problem.application.dto.DuplicateProblemsResponseDto;
import com.first.flash.climbing.problem.application.dto.ProblemCreateResponseDto;
import com.first.flash.climbing.problem.application.dto.ProblemDetailResponseDto;
import com.first.flash.climbing.problem.application.dto.ProblemHoldRequestDto;
import com.first.flash.climbing.problem.application.dto.ProblemPerceivedDifficultyRequestDto;
import com.first.flash.climbing.problem.application.dto.ProblemsResponseDto;
import com.first.flash.climbing.problem.domain.dto.ProblemCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "problems", description = "문제 관리 API")
@RestController
@RequiredArgsConstructor
public class ProblemController {

    private final static String DEFAULT_SIZE = "2";
    private final static String DEFAULT_SORT_BY = "recommend";

    private final ProblemsSaveService problemsSaveService;
    private final ProblemReadService problemReadService;
    private final ProblemsService problemsService;
    private final ProblemsHoldService problemsHoldService;

    @Operation(summary = "문제 생성", description = "특정 섹터에 문제 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 문제 생성함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemCreateResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 형식",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "요청값 누락", value = "{\"name\": \"이미지 URL은 필수입니다.\"}")
            })),
        @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "클라이밍장 없음", value = "{\"error\": \"아이디가 1인 클라이밍장을 찾을 수 없습니다.\"}"),
                @ExampleObject(name = "섹터 없음", value = "{\"error\": \"아이디가 1인 섹터를 찾을 수 없습니다.\"}"),
                @ExampleObject(name = "난이도 없음", value = "{\"error\": \"이름이 핑크인 난이도를 찾을 수 없습니다.\"}")
            }))
    })
    @PostMapping("/gyms/{gymId}/sectors/{sectorId}/problems")
    public ResponseEntity<ProblemCreateResponseDto> saveProblems(
        @PathVariable("gymId") final Long gymId,
        @PathVariable("sectorId") final Long sectorId,
        @Valid @RequestBody final ProblemCreateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(problemsSaveService.saveProblems(gymId, sectorId, requestDto));
    }

    @Operation(summary = "문제 여러건 조회", description = "문제 필터링, 정렬, 페이지네이션 후 여러건 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 문제 조회",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemsResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "클라이밍장 없음", value = "{\"error\": \"아이디가 1인 클라이밍장을 찾을 수 없습니다.\"}")
            }))
    })
    @GetMapping("/gyms/{gymId}/problems")
    public ResponseEntity<ProblemsResponseDto> findAllProblems(
        @PathVariable final Long gymId,
        @RequestParam(name = "cursor", required = false) final String cursor,
        @RequestParam(defaultValue = DEFAULT_SORT_BY, required = false) final String sortBy,
        @RequestParam(defaultValue = DEFAULT_SIZE, required = false) final int size,
        @RequestParam(required = false) final List<String> difficulty,
        @RequestParam(required = false) final List<String> sector,
        @RequestParam(name = "has-solution", required = false) final Boolean hasSolution,
        @RequestParam(name = "is-honey", required = false) final Boolean isHoney) {
        return ResponseEntity.ok(
            problemReadService.findAll(gymId, cursor, sortBy, size, difficulty, sector,
                hasSolution, isHoney));
    }

    @Operation(summary = "문제 단건 조회", description = "특정 문제의 정보 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 문제 정보 조회함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "문제 없음", value = "{\"error\": \"아이디가 0190c558-9063-7050-b4fc-eb421e3236b3인 문제를 찾을 수 없습니다.\"}")
            }))
    })
    @GetMapping("/problems/{problemId}")
    public ResponseEntity<ProblemDetailResponseDto> findProblemById(
        @PathVariable final UUID problemId) {
        return ResponseEntity.ok(problemReadService.viewProblems(problemId));
    }

    @Operation(summary = "문제 체감 난이도 수정", description = "특정 문제의 체감 난이도 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 문제 정보 수정함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 형식",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "요청값 누락", value = "{\"perceivedDifficulty\": \"변경할 체감 난이도 수치는 필수입니다.\"}")
            })),
        @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "문제 없음", value = "{\"error\": \"아이디가 0190c558-9063-7050-b4fc-eb421e3236b3인 문제를 찾을 수 없습니다.\"}")
            }))
    })
    @PatchMapping("/admin/problems/{problemId}/perceivedDifficulty")
    public ResponseEntity<ProblemDetailResponseDto> changePerceivedDifficulty(
        @PathVariable final UUID problemId,
        @Valid @RequestBody final ProblemPerceivedDifficultyRequestDto requestDto) {
        return ResponseEntity.ok(problemsService.setPerceivedDifficulty(problemId, requestDto.perceivedDifficulty()));
    }

    @Operation(summary = "문제 홀드색 수정", description = "특정 문제의 홀드색 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 문제 정보 수정함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 형식",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "요청값 누락", value = "{\"perceivedDifficulty\": \"변경할 홀드 id는 필수입니다.\"}")
            })),
        @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "문제 없음", value = "{\"error\": \"아이디가 0190c558-9063-7050-b4fc-eb421e3236b3인 문제를 찾을 수 없습니다.\"}")
            }))
    })
    @PatchMapping("/admin/problems/{problemId}/holds")
    public ResponseEntity<ProblemDetailResponseDto> changeHold(
        @PathVariable final UUID problemId,
        @Valid @RequestBody final ProblemHoldRequestDto requestDto) {
        return ResponseEntity.ok(problemsHoldService.updateHold(problemId, requestDto.holdId()));
    }

    @Operation(summary = "중복된 문제 조회", description = "sectorId, holdColorId, difficulty로 중복된 문제를 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 중복된 문제 조회",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DuplicateProblemsResponseDto.class)))
    })
    @GetMapping("/problems/duplicate")
    public ResponseEntity<DuplicateProblemsResponseDto> findDuplicateProblems(
        @RequestParam("sectorId") final Long sectorId,
        @RequestParam("holdColorId") final Long holdId,
        @RequestParam("difficulty") final String difficulty) {

        DuplicateProblemsResponseDto response = problemReadService.findDuplicateProblems(sectorId, holdId, difficulty);

        return ResponseEntity.ok(response);
    }
}
