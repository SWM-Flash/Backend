package com.first.flash.climbing.sector.ui;

import com.first.flash.climbing.sector.application.SectorService;
import com.first.flash.climbing.sector.application.dto.SectorCreateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorWriteDetailResponseDto;
import com.first.flash.climbing.sector.application.dto.SectorUpdateRemovalDateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SectorController {

    private final SectorService sectorService;

    @PostMapping("gyms/{gymId}/sectors")
    public ResponseEntity<SectorWriteDetailResponseDto> createSector(@PathVariable final Long gymId,
        @RequestBody final SectorCreateRequestDto sectorCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(sectorService.saveSector(gymId, sectorCreateRequestDto));
    }

    @PatchMapping("sectors/{sectorId}")
    public ResponseEntity<SectorWriteDetailResponseDto> updateSectorRemovalDate(
        @PathVariable final Long sectorId,
        @RequestBody final SectorUpdateRemovalDateRequestDto sectorUpdateRemovalDateRequestDto) {
        SectorWriteDetailResponseDto response = sectorService.updateSectorRemovalDate(
            sectorId, sectorUpdateRemovalDateRequestDto);
        return ResponseEntity.ok(response);
    }
}
