package com.first.flash.climbing.sector.infrastructure;

import com.first.flash.climbing.sector.domain.SectorInfo;
import com.first.flash.climbing.sector.domain.SectorInfoRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SectorInfoRepositoryImpl implements SectorInfoRepository {

    private final SectorInfoJpaRepository jpaRepository;

    @Override
    public SectorInfo save(final SectorInfo sectorInfo) {
        return jpaRepository.save(sectorInfo);
    }

    @Override
    public Optional<SectorInfo> findById(final Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<SectorInfo> findAll() {
        return jpaRepository.findAll();
    }
}
