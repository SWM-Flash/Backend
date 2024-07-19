package com.first.flash.climbing.sector.application;

import com.first.flash.climbing.sector.application.dto.SectorCreateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorUpdateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorDetailResponseDto;
import com.first.flash.climbing.sector.application.dto.SectorUpdateRemovalDateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorsDetailResponseDto;
import com.first.flash.climbing.sector.domain.Sector;
import com.first.flash.climbing.sector.domain.SectorExpiredEvent;
import com.first.flash.climbing.sector.domain.SectorInfoUpdatedEvent;
import com.first.flash.climbing.sector.domain.SectorRemovalDateUpdatedEvent;
import com.first.flash.climbing.sector.domain.SectorRepository;
import com.first.flash.climbing.sector.exception.exceptions.SectorNotFoundException;
import com.first.flash.global.event.Events;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SectorService {

    private final SectorRepository sectorRepository;

    @Transactional
    public SectorDetailResponseDto saveSector(final Long gymId,
        final SectorCreateRequestDto createRequestDto) {
        Sector sector = createSectorByDto(gymId, createRequestDto);
        return SectorDetailResponseDto.toDto(sectorRepository.save(sector));
    }

    @Transactional
    public SectorDetailResponseDto updateSectorRemovalDate(final Long sectorId,
        final SectorUpdateRemovalDateRequestDto sectorUpdateRemovalDateRequestDto) {
        Sector sector = findById(sectorId);
        LocalDate removalDate = sectorUpdateRemovalDateRequestDto.removalDate();
        sector.updateRemovalDate(removalDate);
        Events.raise(SectorRemovalDateUpdatedEvent.of(sectorId, removalDate));
        return SectorDetailResponseDto.toDto(sector);
    }

    @Transactional
    public void expireSector() {
        List<Long> expiredSectorIds = sectorRepository.updateExpiredSector();
        Events.raise(SectorExpiredEvent.of(expiredSectorIds));
    }

    @Transactional
    public SectorDetailResponseDto updateSector(
        final Long sectorId,
        final SectorUpdateRequestDto updateRequestDto) {
        Sector foundSector = findById(sectorId);
        foundSector.updateSector(updateRequestDto.sectorName(), updateRequestDto.adminSectorName(),
            updateRequestDto.settingDate(),
            updateRequestDto.removalDate(), updateRequestDto.gymId());
        Events.raise(SectorInfoUpdatedEvent.of(foundSector.getId(), updateRequestDto.sectorName(),
            updateRequestDto.settingDate()));
        return SectorDetailResponseDto.toDto(foundSector);
    }

    public Sector findById(final Long id) {
        return sectorRepository.findById(id)
                               .orElseThrow(() -> new SectorNotFoundException(id));
    }

    public SectorsDetailResponseDto findAllSectors() {
        List<SectorDetailResponseDto> sectorsResponse = sectorRepository
            .findAll()
            .stream()
            .map(
                SectorDetailResponseDto::toDto)
            .toList();
        return new SectorsDetailResponseDto(sectorsResponse);
    }

    private Sector createSectorByDto(final Long gymId,
        final SectorCreateRequestDto createRequestDto) {
        if (hasNoRemovalDate(createRequestDto)) {
            return Sector.createExceptRemovalDate(createRequestDto.name(),
                createRequestDto.adminName(), createRequestDto.settingDate(), gymId);
        }

        return Sector.createDefault(createRequestDto.name(),
            createRequestDto.adminName(), createRequestDto.settingDate(),
            createRequestDto.removalDate(), gymId);
    }

    private static boolean hasNoRemovalDate(final SectorCreateRequestDto createRequestDto) {
        return Objects.isNull(createRequestDto.removalDate());
    }
}
