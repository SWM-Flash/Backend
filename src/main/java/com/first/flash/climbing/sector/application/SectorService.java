package com.first.flash.climbing.sector.application;

import com.first.flash.climbing.sector.application.dto.SectorCreateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorWriteDetailResponseDto;
import com.first.flash.climbing.sector.application.dto.SectorUpdateRemovalDateRequestDto;
import com.first.flash.climbing.sector.domain.Sector;
import com.first.flash.climbing.sector.domain.SectorRemovalDateUpdatedEvent;
import com.first.flash.climbing.sector.domain.SectorRepository;
import com.first.flash.climbing.sector.exception.exceptions.SectorNotFoundException;
import com.first.flash.global.event.Events;
import java.time.LocalDate;
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
    public SectorWriteDetailResponseDto saveSector(final Long gymId,
        final SectorCreateRequestDto createRequestDto) {
        Sector sector = createSectorByDto(gymId, createRequestDto);
        return SectorWriteDetailResponseDto.toDto(sectorRepository.save(sector));
    }

    @Transactional
    public SectorWriteDetailResponseDto updateSectorRemovalDate(final Long sectorId,
        final SectorUpdateRemovalDateRequestDto sectorUpdateRemovalDateRequestDto) {
        Sector sector = findById(sectorId);
        LocalDate removalDate = sectorUpdateRemovalDateRequestDto.removalDate();
        sector.updateRemovalDate(removalDate);
        Events.raise(SectorRemovalDateUpdatedEvent.of(sectorId, removalDate));
        return SectorWriteDetailResponseDto.toDto(sector);
    }

    public Sector findById(final Long id) {
        return sectorRepository.findById(id)
                               .orElseThrow(() -> new SectorNotFoundException(id));
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
