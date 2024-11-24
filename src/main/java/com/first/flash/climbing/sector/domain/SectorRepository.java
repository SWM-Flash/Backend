package com.first.flash.climbing.sector.domain;

import com.first.flash.climbing.sector.infrastructure.dto.UpdateSectorsDto;
import java.util.List;
import java.util.Optional;

public interface SectorRepository {

    Sector save(final Sector sector);

    Optional<Sector> findById(final Long id);

    List<Long> updateExpiredSector();

    List<Sector> findAll();

    void updateSectors(final Long sectorInfoId, final UpdateSectorsDto updateSectorsDto);

    List<Long> findSectorIdsBySectorInfoId(Long sectorInfoId);
}
