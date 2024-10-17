package com.first.flash.version.ui;

import com.first.flash.climbing.solution.application.dto.SolutionsPageResponseDto;
import com.first.flash.version.application.VersionService;
import com.first.flash.version.application.dto.VersionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "versions", description = "앱 버전 정보 API")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class VersionController {

    private final VersionService versionService;

    @Operation(summary = "버전 정보 조회", description = "앱 최신 버전 정보 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 조회함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VersionResponseDto.class)))
    })
    @GetMapping("versions")
    public ResponseEntity<VersionResponseDto> getLatestVersions() {
        VersionResponseDto versionInfo = versionService.getLatestVersions();
        return ResponseEntity.ok(versionInfo);
    }
}