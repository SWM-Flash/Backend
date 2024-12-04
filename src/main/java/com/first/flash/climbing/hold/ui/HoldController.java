package com.first.flash.climbing.hold.ui;

import com.first.flash.climbing.hold.application.HoldService;
import com.first.flash.climbing.hold.application.dto.HoldCreateRequestDto;
import com.first.flash.climbing.hold.application.dto.HoldResponseDto;
import com.first.flash.climbing.hold.application.dto.HoldsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "holds", description = "홀드 정보 관리 API")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class HoldController {

    private final HoldService holdService;

    @Operation(summary = "모든 홀드 정보 조회", description = "모든 홀드 정보를 리스트로 반환")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 홀드를 조회",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = HoldsResponseDto.class))),
    })
    @GetMapping("holds")
    public ResponseEntity<HoldsResponseDto> findAllHolds() {
        return ResponseEntity.ok(holdService.findAllHolds());
    }

    @Operation(summary = "홀드 정보 생성", description = "홀드 정보를 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "홀드 정보를 생성",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = HoldResponseDto.class))),
    })
    @PostMapping("holds")
    public ResponseEntity<HoldResponseDto> createHold(
        @Valid @RequestBody final HoldCreateRequestDto createRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(holdService.saveHold(createRequestDto));
    }
}