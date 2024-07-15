package com.first.flash.climbing.gym.infrastructure;

import static com.first.flash.climbing.sector.domain.QSector.sector;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClimbingGymQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<String> findSectorNamesByGymId(final Long gymId) {
        return jpaQueryFactory.select(sector.sectorName.name)
                              .from(sector)
                              .where(sector.gymId.eq(gymId), sector.removalInfo.isExpired.isFalse())
                              .fetch();
    }
}
