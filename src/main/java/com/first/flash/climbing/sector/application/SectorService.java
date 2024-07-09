package com.first.flash.climbing.sector.application;

import com.first.flash.climbing.sector.application.dto.SectorCreateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorResponseDto;
import com.first.flash.climbing.sector.domain.Sector;
import com.first.flash.climbing.sector.domain.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SectorService {

    private final SectorRepository sectorRepository;


    public SectorResponseDto saveSector(final SectorCreateRequestDto sectorCreateRequestDto) {
        Sector newSector = sectorCreateRequestDto.toEntity();
        return SectorResponseDto.toDto(sectorRepository.save(newSector));
    }
}
