package com.first.flash.climbing.problem.infrastructure;

import static com.first.flash.climbing.problem.domain.QQueryProblem.queryProblem;
import static com.first.flash.climbing.problem.infrastructure.paging.SortBy.DIFFICULTY;
import static com.first.flash.climbing.problem.infrastructure.paging.SortBy.VIEWS;

import com.first.flash.climbing.problem.domain.QueryProblem;
import com.first.flash.climbing.problem.infrastructure.paging.Cursor;
import com.first.flash.climbing.problem.infrastructure.paging.SortBy;
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

    public List<QueryProblem> findAll(final Cursor prevCursor, final SortBy sortBy, final int size,
        final Long gymId, final List<String> difficulty, final List<String> sector,
        final Boolean hasSolution) {
        return queryFactory
            .selectFrom(queryProblem)
            .where(notExpired(), cursorCondition(prevCursor), inGym(gymId), inSectors(sector),
                inDifficulties(difficulty), hasSolution(hasSolution))
            .orderBy(sortItem(sortBy), queryProblem.id.desc())
            .limit(size)
            .fetch();
    }

    public void updateRemovalDateBySectorId(final Long sectorId, final LocalDate removalDate) {
        queryFactory.update(queryProblem)
                    .set(queryProblem.removalDate, removalDate)
                    .where(queryProblem.sectorId.eq(sectorId))
                    .execute();
    }

    private BooleanExpression inGym(final Long gymId) {
        return queryProblem.gymId.eq(gymId);
    }

    private BooleanExpression cursorCondition(final Cursor prevCursor) {
        if (Objects.isNull(prevCursor) || Objects.isNull(prevCursor.cursorValue())) {
            return null;
        }
        SortBy sortBy = prevCursor.sortBy();
        String cursorValue = prevCursor.cursorValue();
        UUID cursorId = prevCursor.lastId();
        return getExpressionBySortColumn(sortBy, cursorValue, cursorId);
    }

    private BooleanExpression getExpressionBySortColumn(
        final SortBy sortBy, final String cursorValue, final UUID cursorId) {
        if (sortBy.equals(DIFFICULTY)) {
            return queryProblem.difficultyLevel.gt(Integer.parseInt(cursorValue))
                                               .or(queryProblem.difficultyLevel
                                                   .eq(Integer.parseInt(cursorValue))
                                                   .and(queryProblem.id.lt(cursorId)));
        }
        if (sortBy.equals(VIEWS)) {
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

    private OrderSpecifier<?> sortItem(final SortBy sortBy) {
        if (sortBy.equals(DIFFICULTY)) {
            return queryProblem.difficultyLevel.asc();
        }
        if (sortBy.equals(VIEWS)) {
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
}
