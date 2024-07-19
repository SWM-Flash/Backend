package com.first.flash.climbing.sector.infrastructure;

import com.first.flash.climbing.sector.domain.Sector;
import com.first.flash.climbing.sector.domain.SectorRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeSectorRepository implements SectorRepository {

    private Map<Long, Sector> db = new HashMap<>();
    private Long id = 0L;

    @Override
    public Sector save(final Sector sector) {
        db.put(id, sector);
        return new Sector(id++, sector.getSectorName(), sector.getSettingDate(),
            sector.getRemovalInfo(), sector.getGymId());
    }

    @Override
    public Optional<Sector> findById(final Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Long> updateExpiredSector() {
        List<Long> expiredIds = db.keySet().stream()
                                  .filter(key -> db.get(key).getRemovalInfo().getRemovalDate()
                                                   .isBefore(db.get(key).getSettingDate()))
                                  .toList();
        expiredIds.forEach(db::remove);
        return expiredIds;
    }

    @Override
    public List<Sector> findAll() {
        return List.copyOf(db.values());
    }
}
