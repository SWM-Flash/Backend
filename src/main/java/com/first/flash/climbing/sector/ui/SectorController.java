package com.first.flash.climbing.sector.ui;

import com.first.flash.climbing.sector.application.SectorService;
import com.first.flash.climbing.sector.application.dto.SectorCreateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorUpdateRemovalDateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorWriteDetailResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "sectors", description = "섹터 관리 API")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class SectorController {

    private final SectorService sectorService;

    @Operation(summary = "섹터 갱신(생성)", description = "특정 클라이밍장에 새로운 섹터 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 섹터를 생성함"),
        @ApiResponse(responseCode = "400", description = "탈거일이 세팅일보다 빠름")
    })
    @PostMapping("gyms/{gymId}/sectors")
    public ResponseEntity<SectorWriteDetailResponseDto> createSector(@PathVariable final Long gymId,
        @RequestBody final SectorCreateRequestDto sectorCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(sectorService.saveSector(gymId, sectorCreateRequestDto));
    }

    @Operation(summary = "섹터 탈거일 수정", description = "특정 섹터의 탈거일 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 섹터 탈거일을 수정함"),
        @ApiResponse(responseCode = "400", description = "탈거일이 세팅일보다 빠름"),
        @ApiResponse(responseCode = "404", description = "섹터를 찾을 수 없음")
    })
    @PatchMapping("sectors/{sectorId}")
    public ResponseEntity<SectorWriteDetailResponseDto> updateSectorRemovalDate(
        @PathVariable final Long sectorId,
        @RequestBody final SectorUpdateRemovalDateRequestDto sectorUpdateRemovalDateRequestDto) {
        SectorWriteDetailResponseDto response = sectorService.updateSectorRemovalDate(
            sectorId, sectorUpdateRemovalDateRequestDto);
        return ResponseEntity.ok(response);
    }
}
