package com.first.flash.climbing.sector.infrastructure;

import com.first.flash.climbing.sector.domain.Sector;
import com.first.flash.climbing.sector.domain.SectorRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeSectorRepository implements SectorRepository {

    private Map<Long, Sector> db = new HashMap<>();
    private Long id = 0L;

    @Override
    public Long save(final Sector sector) {
        db.put(id, sector);
        return id++;
    }

    @Override
    public Optional<Sector> findById(final Long id) {
        return Optional.ofNullable(db.get(id));
    }
}
