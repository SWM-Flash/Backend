package com.first.flash.climbing.gym.infrastructure;

import static com.first.flash.climbing.sector.domain.QSector.sector;

import com.first.flash.climbing.gym.infrastructure.dto.SectorInfoResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClimbingGymQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<SectorInfoResponseDto> findSortedSectorNamesByGymId(final Long gymId) {
        return jpaQueryFactory.select(
                                  Projections.constructor(SectorInfoResponseDto.class, sector.sectorName.name,
                                      sector.selectedImageUrl))
                              .from(sector)
                              .where(sector.gymId.eq(gymId), sector.removalInfo.isExpired.isFalse())
                              .distinct()
                              .orderBy(sector.sectorName.name.asc())
                              .fetch();
    }
}
