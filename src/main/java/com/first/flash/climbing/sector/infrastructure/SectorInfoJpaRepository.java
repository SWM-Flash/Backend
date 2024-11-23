package com.first.flash.climbing.sector.infrastructure;

import com.first.flash.climbing.sector.domain.SectorInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorInfoJpaRepository extends JpaRepository<SectorInfo, Long> {

    SectorInfo save(final SectorInfo sector);

    Optional<SectorInfo> findById(final Long id);

    List<SectorInfo> findAll();
}
