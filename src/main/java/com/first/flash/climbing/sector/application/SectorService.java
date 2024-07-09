package com.first.flash.climbing.sector.application;

import com.first.flash.climbing.sector.application.dto.SectorCreateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorResponseDto;
import com.first.flash.climbing.sector.domain.Sector;
import com.first.flash.climbing.sector.domain.SectorRepository;
import com.first.flash.climbing.sector.exception.exceptions.SectorNotFoundException;
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

    public Sector findById(final Long id) {
        return sectorRepository.findById(id)
                               .orElseThrow(() -> new SectorNotFoundException(id));
    }
}
