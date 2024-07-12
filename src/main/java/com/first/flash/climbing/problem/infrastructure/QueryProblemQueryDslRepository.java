package com.first.flash.climbing.problem.infrastructure;

import static com.first.flash.climbing.problem.domain.QQueryProblem.queryProblem;

import com.first.flash.climbing.problem.domain.QueryProblem;
import com.first.flash.climbing.problem.infrastructure.dto.Cursor;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueryProblemQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<QueryProblem> findAll(final Cursor prevCursor, final String sort, final int size,
        final List<String> difficulty, final List<String> sector, final Boolean hasSolution) {
        return queryFactory
            .selectFrom(queryProblem)
            .where(notExpired(), cursorCondition(prevCursor), inSector(sector),
                inDifficulty(difficulty), hasSolution(hasSolution))
            .orderBy(sortItem(sort))
            .orderBy(queryProblem.id.desc())
            .limit(size)
            .fetch();
    }

    private BooleanExpression cursorCondition(final Cursor prevCursor) {
        if (Objects.isNull(prevCursor) || Objects.isNull(prevCursor.cursorValue())) {
            return null;
        }

        String sortBy = prevCursor.sortBy();
        String cursorValue = prevCursor.cursorValue();
        UUID cursorId = prevCursor.lastId();

        switch (sortBy) {
            case "views":
                return queryProblem.views.lt(Integer.parseInt(cursorValue))
                                         .or(queryProblem.views.eq(Integer.parseInt(cursorValue))
                                                               .and(queryProblem.id.lt(cursorId)));
            case "difficulty":
                return queryProblem.difficultyLevel.lt(Integer.parseInt(cursorValue))
                                                   .or(queryProblem.difficultyLevel.eq(
                                                       Integer.parseInt(cursorValue)).and(
                                                       queryProblem.id.lt(cursorId)));
            default:
                return queryProblem.id.lt(cursorId);
        }
    }

    private BooleanExpression notExpired() {
        return queryProblem.isExpired.isFalse();
    }

    private OrderSpecifier<?> sortItem(final String sort) {
        switch (sort) {
            case "views":
                return queryProblem.views.desc();
            case "difficulty":
                return queryProblem.difficultyLevel.desc();
            default:
                return queryProblem.id.desc();
        }
    }

    private BooleanExpression inSector(final List<String> sector) {
        if (Objects.isNull(sector)) {
            return null;
        }
        return queryProblem.sectorName.in(sector);
    }

    private BooleanExpression inDifficulty(final List<String> difficulty) {
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
