package com.first.flash.climbing.sector.domain;

import java.util.List;
import java.util.Optional;

public interface SectorInfoRepository {

    SectorInfo save(SectorInfo sectorInfo);

    Optional<SectorInfo> findById(Long id);

    List<SectorInfo> findAll();
}
