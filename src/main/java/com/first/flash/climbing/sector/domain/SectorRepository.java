package com.first.flash.climbing.sector.domain;

import java.util.Optional;

public interface SectorRepository {

    Sector save(final Sector sector);

    Optional<Sector> findById(final Long id);
}
