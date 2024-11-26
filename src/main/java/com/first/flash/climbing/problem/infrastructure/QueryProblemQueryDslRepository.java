package com.first.flash.climbing.problem.infrastructure;

import static com.first.flash.climbing.problem.domain.QQueryProblem.queryProblem;
import static com.first.flash.climbing.problem.infrastructure.paging.ProblemSortBy.DIFFICULTY;
import static com.first.flash.climbing.problem.infrastructure.paging.ProblemSortBy.VIEWS;

import com.first.flash.climbing.problem.domain.QueryProblem;
import com.first.flash.climbing.problem.infrastructure.paging.ProblemCursor;
import com.first.flash.climbing.problem.infrastructure.paging.ProblemSortBy;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueryProblemQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<QueryProblem> findAll(final ProblemCursor prevProblemCursor,
        final ProblemSortBy problemSortBy, final int size,
        final Long gymId, final List<String> difficulty, final List<String> sector,
        final Boolean hasSolution, final Boolean isHoney) {
        return queryFactory
            .selectFrom(queryProblem)
            .where(notExpired(), cursorCondition(prevProblemCursor), inGym(gymId),
                inSectors(sector),
                inDifficulties(difficulty), hasSolution(hasSolution), isHoneyCondition(isHoney))
            .orderBy(sortItem(problemSortBy), queryProblem.id.desc())
            .limit(size)
            .fetch();
    }

    public void updateRemovalDateBySectorId(final Long sectorId, final LocalDate removalDate,
        final boolean isExpired) {
        queryFactory.update(queryProblem)
                    .set(queryProblem.removalDate, removalDate)
                    .set(queryProblem.isExpired, isExpired)
                    .set(queryProblem.isFakeRemovalDate, false)
                    .where(queryProblem.sectorId.eq(sectorId))
                    .execute();
    }

    public void expireProblemsBySectorIds(final List<Long> expiredSectorsIds) {
        queryFactory.update(queryProblem)
                    .set(queryProblem.isExpired, true)
                    .where(queryProblem.sectorId.in(expiredSectorsIds))
                    .execute();
    }

    public void updateQueryProblemInfo(final Long sectorId, final String sectorName,
        final LocalDate settingDate, final boolean isExpired) {
        queryFactory.update(queryProblem)
                    .set(queryProblem.sectorName, sectorName)
                    .set(queryProblem.settingDate, settingDate)
                    .set(queryProblem.isExpired, isExpired)
                    .where(queryProblem.sectorId.eq(sectorId))
                    .execute();
    }

    private BooleanExpression inGym(final Long gymId) {
        return queryProblem.gymId.eq(gymId);
    }

    private BooleanExpression isHoneyCondition(final Boolean isHoney) {
        if (Boolean.TRUE.equals(isHoney)) {
            return queryProblem.perceivedDifficulty.lt(0);
        }
        return null;
    }

    private BooleanExpression cursorCondition(final ProblemCursor prevProblemCursor) {
        if (Objects.isNull(prevProblemCursor) || Objects.isNull(prevProblemCursor.cursorValue())) {
            return null;
        }
        ProblemSortBy problemSortBy = prevProblemCursor.problemSortBy();
        String cursorValue = prevProblemCursor.cursorValue();
        UUID cursorId = prevProblemCursor.lastId();
        return getExpressionBySortColumn(problemSortBy, cursorValue, cursorId);
    }

    private BooleanExpression getExpressionBySortColumn(
        final ProblemSortBy problemSortBy, final String cursorValue, final UUID cursorId) {
        if (problemSortBy.equals(DIFFICULTY)) {
            return queryProblem.difficultyLevel.gt(Integer.parseInt(cursorValue))
                                               .or(queryProblem.difficultyLevel
                                                   .eq(Integer.parseInt(cursorValue))
                                                   .and(queryProblem.id.lt(cursorId)));
        }
        if (problemSortBy.equals(VIEWS)) {
            return queryProblem.views.lt(Integer.parseInt(cursorValue))
                                     .or(queryProblem.views.eq(Integer.parseInt(cursorValue))
                                                           .and(queryProblem.id.lt(cursorId)));
        }
        return queryProblem.recommendationValue.lt(Integer.parseInt(cursorValue))
                                               .or(queryProblem.recommendationValue
                                                   .eq(Long.parseLong(cursorValue))
                                                   .and(queryProblem.id.lt(cursorId)));
    }

    private BooleanExpression notExpired() {
        return queryProblem.isExpired.isFalse();
    }

    private OrderSpecifier<?> sortItem(final ProblemSortBy problemSortBy) {
        if (problemSortBy.equals(DIFFICULTY)) {
            return queryProblem.difficultyLevel.asc();
        }
        if (problemSortBy.equals(VIEWS)) {
            return queryProblem.views.desc();
        }
        return queryProblem.recommendationValue.desc();
    }

    private BooleanExpression inSectors(final List<String> sector) {
        if (Objects.isNull(sector)) {
            return null;
        }
        return queryProblem.sectorName.in(sector);
    }

    private BooleanExpression inDifficulties(final List<String> difficulty) {
        if (Objects.isNull(difficulty)) {
            return null;
        }
        return queryProblem.difficultyName.in(difficulty);
    }

    private BooleanExpression hasSolution(final Boolean hasSolution) {
        if (Objects.isNull(hasSolution)) {
            return null;
        }
        return queryProblem.hasSolution.eq(hasSolution);
    }

    public void updateSectorNameBySectorIds(final List<Long> sectorIds, final String sectorName) {
        queryFactory.update(queryProblem)
                    .set(queryProblem.sectorName, sectorName)
                    .where(queryProblem.sectorId.in(sectorIds))
                    .execute();
    }
}
