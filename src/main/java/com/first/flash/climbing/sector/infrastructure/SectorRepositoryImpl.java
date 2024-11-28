package com.first.flash.climbing.sector.infrastructure;

import com.first.flash.climbing.sector.domain.Sector;
import com.first.flash.climbing.sector.domain.SectorRepository;
import com.first.flash.climbing.sector.infrastructure.dto.UpdateSectorsDto;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SectorRepositoryImpl implements SectorRepository {

    private final SectorJpaRepository sectorJpaRepository;
    private final SectorQueryDslRepository sectorQueryDslRepository;

    @Override
    public Sector save(final Sector sector) {
        return sectorJpaRepository.save(sector);
    }

    @Override
    public Optional<Sector> findById(final Long id) {
        return sectorJpaRepository.findById(id);
    }

    @Override
    public List<Long> updateExpiredSector() {
        return sectorQueryDslRepository.updateExpiredSector();
    }

    @Override
    public List<Sector> findAll() {
        return sectorJpaRepository.findAll();
    }

    @Override
    public void updateSectors(final Long sectorInfoId,
        final UpdateSectorsDto updateSectorsDto) {
        sectorQueryDslRepository.updateSectors(sectorInfoId, updateSectorsDto);
    }

    @Override
    public List<Long> findSectorIdsBySectorInfoId(final Long sectorInfoId) {
        return sectorQueryDslRepository.findSectorIdsBySectorInfoId(sectorInfoId);
    }
}
