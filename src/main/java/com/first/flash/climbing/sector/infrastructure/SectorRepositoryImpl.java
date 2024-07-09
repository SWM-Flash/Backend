package com.first.flash.climbing.sector.infrastructure;

import com.first.flash.climbing.sector.domain.Sector;
import com.first.flash.climbing.sector.domain.SectorRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SectorRepositoryImpl implements SectorRepository {

    private final SectorJpaRepository sectorJpaRepository;

    @Override
    public Long save(final Sector sector) {
        return sectorJpaRepository.save(sector);
    }

    @Override
    public Optional<Sector> findById(final Long id) {
        return sectorJpaRepository.findById(id);
    }
}
