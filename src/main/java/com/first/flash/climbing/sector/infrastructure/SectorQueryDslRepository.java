package com.first.flash.climbing.sector.infrastructure;

import static com.first.flash.climbing.sector.domain.QSector.sector;

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
                              .where(isExpired())
                              .fetch();
    }

    private static BooleanExpression isExpired() {
        return sector.removalInfo.isExpired.isFalse()
                                           .and(
                                               sector.removalInfo.removalDate
                                                   .before(LocalDate.now()));
    }
}
