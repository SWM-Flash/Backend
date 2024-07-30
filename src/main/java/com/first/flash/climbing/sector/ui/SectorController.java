package com.first.flash.climbing.sector.ui;

import com.first.flash.climbing.sector.application.SectorService;
import com.first.flash.climbing.sector.application.dto.SectorCreateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorDetailResponseDto;
import com.first.flash.climbing.sector.application.dto.SectorUpdateRemovalDateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorUpdateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorsDetailResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "sectors", description = "섹터 관리 API")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class SectorController {

    private final SectorService sectorService;

    @Operation(summary = "모든 섹터 조회", description = "모든 섹터 정보를 리스트로 반환")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 섹터를 조회",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SectorsDetailResponseDto.class))),
    })
    @GetMapping("sectors")
    public ResponseEntity<SectorsDetailResponseDto> findAllSectors() {
        return ResponseEntity.ok(sectorService.findAllSectors());
    }

    @Operation(summary = "섹터 갱신(생성)", description = "특정 클라이밍장에 새로운 섹터 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 섹터를 생성함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SectorDetailResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 형식",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "요청값 누락", value = "{\"name\": \"섹터 이름은 필수입니다.\"}"),
                @ExampleObject(name = "탈거일이 세팅일보다 빠름", value = "{\"error\": \"탈거일이 세팅일보다 빠를 수 없습니다.\"}")
            })),
        @ApiResponse(responseCode = "404", description = "클라이밍장을 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "클라이밍장 없음", value = "{\"error\": \"아이디가 1인 클라이밍장을 찾을 수 없습니다.\"}")
            }))
    })
    @PostMapping("gyms/{gymId}/sectors")
    public ResponseEntity<SectorDetailResponseDto> createSector(@PathVariable final Long gymId,
        @Valid @RequestBody final SectorCreateRequestDto sectorCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(sectorService.saveSector(gymId, sectorCreateRequestDto));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 섹터 탈거일을 수정함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SectorDetailResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 형식",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "요청값 누락", value = "{\"name\": \"탈거일 수정 시 탈거일 정보는 비어있을 수 없습니다.\"}"),
                @ExampleObject(name = "탈거일이 세팅일보다 빠름", value = "{\"error\": \"탈거일이 세팅일보다 빠를 수 없습니다.\"}")
            })),
        @ApiResponse(responseCode = "404", description = "섹터를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "섹터 없음", value = "{\"error\": \"아이디가 1인 섹터를 찾을 수 없습니다.\"}")
            }))
    })
    @PatchMapping("sectors/{sectorId}")
    public ResponseEntity<SectorDetailResponseDto> updateSectorRemovalDate(
        @PathVariable final Long sectorId,
        @Valid @RequestBody final SectorUpdateRemovalDateRequestDto sectorUpdateRemovalDateRequestDto) {
        SectorDetailResponseDto response = sectorService.updateSectorRemovalDate(
            sectorId, sectorUpdateRemovalDateRequestDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "섹터 전체 수정", description = "특정 섹터의 정보 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 섹터 정보 수정함"),
    })
    @PutMapping("sectors/{sectorId}")
    public ResponseEntity<SectorDetailResponseDto> updateSector(
        @PathVariable final Long sectorId,
        @RequestBody final SectorUpdateRequestDto updateRequestDto) {
        return ResponseEntity.ok(sectorService.updateSector(sectorId, updateRequestDto));
    }
}
