package com.first.flash.climbing.sector.application;

import com.first.flash.climbing.gym.domian.ClimbingGymIdConfirmRequestedEvent;
import com.first.flash.climbing.sector.application.dto.SectorInfosDetailResponseDto;
import com.first.flash.climbing.sector.domain.SectorFixedInfoUpdatedEvent;
import com.first.flash.climbing.sector.infrastructure.dto.UpdateSectorsDto;
import com.first.flash.climbing.sector.application.dto.SectorCreateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorDetailResponseDto;
import com.first.flash.climbing.sector.application.dto.SectorInfoCreateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorInfoDetailResponseDto;
import com.first.flash.climbing.sector.application.dto.SectorUpdateRemovalDateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorUpdateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorsDetailResponseDto;
import com.first.flash.climbing.sector.domain.Sector;
import com.first.flash.climbing.sector.domain.SectorExpiredEvent;
import com.first.flash.climbing.sector.domain.SectorInfo;
import com.first.flash.climbing.sector.domain.SectorInfoRepository;
import com.first.flash.climbing.sector.domain.SectorInfoUpdatedEvent;
import com.first.flash.climbing.sector.domain.SectorRemovalDateUpdatedEvent;
import com.first.flash.climbing.sector.domain.SectorRepository;
import com.first.flash.climbing.sector.exception.exceptions.SectorInfoNotFoundException;
import com.first.flash.climbing.sector.exception.exceptions.SectorNotFoundException;
import com.first.flash.global.event.Events;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SectorService {

    private final SectorRepository sectorRepository;
    private final SectorInfoRepository sectorInfoRepository;

    @Transactional
    public SectorDetailResponseDto saveSector(final Long sectorInfoId,
        final SectorCreateRequestDto createRequestDto) {
        SectorInfo sectorInfo = findSectorInfoById(sectorInfoId);
        Sector sector = createSector(sectorInfo, createRequestDto);
        return SectorDetailResponseDto.toDto(sectorRepository.save(sector));
    }

    @Transactional
    public SectorInfoDetailResponseDto saveSectorInfo(final Long gymId,
        final SectorInfoCreateRequestDto createRequestDto) {
        SectorInfo sectorInfo = SectorInfo.createDefault(createRequestDto.name(),
            createRequestDto.adminName(), gymId, createRequestDto.selectedImageUrl());
        Events.raise(ClimbingGymIdConfirmRequestedEvent.of(gymId));
        return SectorInfoDetailResponseDto.toDto(sectorInfoRepository.save(sectorInfo));
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
            updateRequestDto.settingDate(), updateRequestDto.removalDate(),
            updateRequestDto.selectedImageUrl());
        Events.raise(SectorInfoUpdatedEvent.of(foundSector.getId(), updateRequestDto.sectorName(),
            updateRequestDto.settingDate(), foundSector.isExpired()));
        return SectorDetailResponseDto.toDto(foundSector);
    }

    @Transactional
    public SectorInfoDetailResponseDto updateSectorInfo(final Long sectorInfoId,
        final SectorInfoCreateRequestDto updateRequestDto) {
        SectorInfo sectorInfo = findSectorInfoById(sectorInfoId);
        sectorInfo.updateSectorInfo(updateRequestDto.name(), updateRequestDto.adminName(),
            updateRequestDto.selectedImageUrl());
        UpdateSectorsDto updateSectorsDto = UpdateSectorsDto.toDto(sectorInfo);
        sectorRepository.updateSectors(sectorInfoId, updateSectorsDto);
        List<Long> sectorIds = sectorRepository.findSectorIdsBySectorInfoId(sectorInfoId);
        Events.raise(SectorFixedInfoUpdatedEvent.of(sectorIds, sectorInfo));
        return SectorInfoDetailResponseDto.toDto(sectorInfo);
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

    public SectorInfosDetailResponseDto findAllSectorInfos() {
        List<SectorInfoDetailResponseDto> sectorInfosResponse = sectorInfoRepository
            .findAll()
            .stream()
            .map(
                SectorInfoDetailResponseDto::toDto)
            .toList();
        return new SectorInfosDetailResponseDto(sectorInfosResponse);
    }

    private Sector createSector(final SectorInfo sectorInfo,
        final SectorCreateRequestDto createRequestDto) {
        return Sector.of(sectorInfo.getSectorName(), createRequestDto.settingDate(),
            createRequestDto.removalDate(), sectorInfo.getGymId(),
            sectorInfo.getSelectedImageUrl(), sectorInfo.getId());
    }

    private SectorInfo findSectorInfoById(final Long sectorInfoId) {
        return sectorInfoRepository.findById(sectorInfoId)
                                   .orElseThrow(
                                       () -> new SectorInfoNotFoundException(sectorInfoId));
    }
}
