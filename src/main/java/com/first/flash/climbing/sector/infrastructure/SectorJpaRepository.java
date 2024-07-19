package com.first.flash.climbing.sector.infrastructure;

import com.first.flash.climbing.sector.domain.Sector;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorJpaRepository extends JpaRepository<Sector, Long> {

    Sector save(final Sector sector);

    Optional<Sector> findById(final Long id);

    List<Sector> findAll();
}
