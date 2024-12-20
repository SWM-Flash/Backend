package com.first.flash.climbing.gym.infrastructure;

import static com.first.flash.climbing.gym.domian.QClimbingGym.climbingGym;
import static com.first.flash.climbing.sector.domain.QSector.sector;

import com.first.flash.climbing.gym.infrastructure.dto.ClimbingGymResponseDto;
import com.first.flash.climbing.gym.infrastructure.dto.SectorInfoResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
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
                                  Projections.constructor(SectorInfoResponseDto.class, sector.id,
                                      sector.sectorName.name, sector.selectedImageUrl))
                              .from(sector)
                              .where(sector.gymId.eq(gymId), sector.removalInfo.isExpired.isFalse())
                              .distinct()
                              .orderBy(sector.sectorName.name.asc())
                              .fetch();
    }

    public List<ClimbingGymResponseDto> findAllWithFavorites(final List<Long> favoriteGymIds) {
        return jpaQueryFactory.select(
                                  Projections.constructor(ClimbingGymResponseDto.class, climbingGym.id,
                                      climbingGym.gymName, climbingGym.thumbnailUrl, climbingGym.id.in(favoriteGymIds))
                              )
                              .from(climbingGym)
                              .orderBy(
                                  new CaseBuilder()
                                      .when(climbingGym.id.in(favoriteGymIds)).then(1)
                                      .otherwise(0).desc(),
                                  climbingGym.gymName.asc()
                              )
                              .fetch();
    }
}
