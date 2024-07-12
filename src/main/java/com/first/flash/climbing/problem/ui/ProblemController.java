package com.first.flash.climbing.problem.ui;

import com.first.flash.climbing.problem.application.ProblemsSaveService;
import com.first.flash.climbing.problem.application.dto.ProblemCreateResponseDto;
import com.first.flash.climbing.problem.domain.dto.ProblemCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "problems", description = "문제 관리 API")
@RestController
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemsSaveService problemsSaveService;

    @Operation(summary = "문제 업로드", description = "특정 섹터에 문제 업로드")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 문제를 업로드함"),
    })
    @PostMapping("/gyms/{gymId}/sectors/{sectorId}/problems")
    public ResponseEntity<ProblemCreateResponseDto> saveProblems(
        @PathVariable("gymId") final Long gymId,
        @PathVariable("sectorId") final Long sectorId,
        @RequestBody final ProblemCreateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(problemsSaveService.saveProblems(gymId, sectorId, requestDto));
    }
}
