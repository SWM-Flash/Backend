package com.first.flash.climbing.sector.infrastructure;

import static com.first.flash.climbing.sector.domain.QSector.sector;

import com.first.flash.climbing.sector.infrastructure.dto.UpdateSectorsDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SectorQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Long> updateExpiredSector() {
        List<Long> expiredSectorIds = findExpiredSectorIds();
        jpaQueryFactory.update(sector)
                       .set(sector.removalInfo.isExpired, true)
                       .where(sector.id.in(expiredSectorIds))
                       .execute();
        return expiredSectorIds;
    }

    private List<Long> findExpiredSectorIds() {
        return jpaQueryFactory.select(sector.id)
                              .from(sector)
                              .where(isExpired())
                              .fetch();
    }

    private BooleanExpression isExpired() {
        return sector.removalInfo.isExpired.isFalse()
                                           .and(
                                               sector.removalInfo.removalDate
                                                   .before(LocalDate.now()));
    }

    public void updateSectors(final Long sectorInfoId,
        final UpdateSectorsDto updateSectorsDto) {
        List<Long> sectorIds = findSectorIdsBySectorInfoId(sectorInfoId);
        jpaQueryFactory.update(sector)
                       .set(sector.sectorName.name, updateSectorsDto.name())
                       .set(sector.sectorName.adminName, updateSectorsDto.adminName())
                       .where(sector.id.in(sectorIds))
                       .execute();
    }

    public List<Long> findSectorIdsBySectorInfoId(final Long sectorInfoId) {
        return jpaQueryFactory.select(sector.id)
                              .from(sector)
                              .where(sector.sectorInfoId.eq(sectorInfoId))
                              .fetch();
    }
}
