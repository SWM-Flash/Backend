package com.first.flash.climbing.solution.ui;

import com.first.flash.climbing.solution.application.SolutionSaveService;
import com.first.flash.climbing.solution.application.SolutionService;
import com.first.flash.climbing.solution.application.dto.SolutionUpdateRequestDto;
import com.first.flash.climbing.solution.application.dto.SolutionWriteResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionsPageResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionsResponseDto;
import com.first.flash.climbing.solution.application.dto.UnregisteredMemberSolutionCreateRequest;
import com.first.flash.climbing.solution.domain.dto.SolutionCreateRequestDto;
import com.first.flash.climbing.solution.infrastructure.dto.DetailSolutionDto;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "solutions", description = "해설 관리 API")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class SolutionController {

    private final static String DEFAULT_SIZE = "20";

    private final SolutionService solutionService;
    private final SolutionSaveService solutionSaveService;

    @Operation(summary = "내 해설 조회", description = "본인이 등록한 해설 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 해설을 조회함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SolutionsPageResponseDto.class)))
    })
    @GetMapping("solutions")
    public ResponseEntity<SolutionsPageResponseDto> getMySolutions(
        @RequestParam(name = "cursor", required = false) final String cursor,
        @RequestParam(defaultValue = DEFAULT_SIZE, required = false) final int size,
        @RequestParam(required = false) final Long gymId,
        @RequestParam(required = false) final List<String> difficulty
    ) {
        SolutionsPageResponseDto response = solutionService.findMySolutions(cursor, size, gymId,
            difficulty);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "해설 디테일 조회", description = "해설 id로 상세 정보 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 해설 디테일을 조회함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DetailSolutionDto.class)))
    })
    @GetMapping("solutions/{solutionId}")
    public ResponseEntity<DetailSolutionDto> getDetailSolution(
        @PathVariable final Long solutionId) {
        DetailSolutionDto response = solutionService.findDetailSolutionById(solutionId);
        return ResponseEntity.ok(response);
    }

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
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SolutionWriteResponseDto.class))),
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
    public ResponseEntity<SolutionWriteResponseDto> createSolution(@PathVariable final UUID problemId,
        @Valid @RequestBody final SolutionCreateRequestDto solutionCreateRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(
                                 solutionSaveService.saveSolution(problemId,
                                     solutionCreateRequestDto));
    }

    @Operation(summary = "없는 유저의 영상으로 해설 업로드", description = "없는 유저의 영상으로 해설 업로드")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 해설을 업로드함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SolutionWriteResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 형식",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "요청값 누락", value = "{\"videoUrl\": \"비디오 URL은 필수입니다.\"}"),
            })),
        @ApiResponse(responseCode = "404", description = "문제를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "문제 없음", value = "{\"error\": \"아이디가 0190c558-9063-7050-b4fc-eb421e3236b3인 문제를 찾을 수 없습니다.\"}")
            }))
    })
    @PostMapping("admin/problems/{problemId}/solutions")
    public ResponseEntity<SolutionWriteResponseDto> createUnregisteredMemberSolution(
        @PathVariable final UUID problemId,
        @Valid @RequestBody final UnregisteredMemberSolutionCreateRequest createRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(
                                 solutionSaveService.saveUnregisteredMemberSolution(problemId,
                                     createRequestDto));
    }

    @Operation(summary = "해설 수정", description = "특정 해설 정보 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 해설을 수정함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SolutionWriteResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 형식",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "요청값 누락", value = "{\"videoUrl\": \"비디오 URL은 필수입니다.\"}"),
            })),
        @ApiResponse(responseCode = "403", description = "본인의 해설이 아님",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "수정 권한 없음", value = "{\"error\": \"해당 해설에 접근할 권한이 없습니다.\"}"),
            })),
        @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "해설 없음", value = "{\"error\": \"아이디가 1인 해설을 찾을 수 없습니다.\"}")
            }))
    })
    @PatchMapping("/solutions/{solutionId}")
    public ResponseEntity<SolutionWriteResponseDto> updateSolution(@PathVariable Long solutionId,
        @Valid @RequestBody final SolutionUpdateRequestDto solutionUpdateRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(
                                 solutionService.updateContent(solutionId, solutionUpdateRequestDto)
                             );
    }

    @Operation(summary = "해설 삭제", description = "특정 해설 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "성공적으로 해설을 수정함",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "403", description = "본인의 해설이 아님",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "삭제 권한 없음", value = "{\"error\": \"해당 해설에 접근할 권한이 없습니다.\"}"),
            })),
        @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "문제 없음", value = "{\"error\": \"아이디가 0190c558-9063-7050-b4fc-eb421e3236b3인 문제를 찾을 수 없습니다.\"}"),
                @ExampleObject(name = "해설 없음", value = "{\"error\": \"아이디가 1인 해설을 찾을 수 없습니다.\"}")
            }))
    })
    @DeleteMapping("/solutions/{solutionId}")
    public ResponseEntity<Void> deleteSolution(@PathVariable Long solutionId) {
        solutionService.deleteSolution(solutionId);
        return ResponseEntity.noContent().build();
    }
}
