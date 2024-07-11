package com.first.flash.climbing.problem.infrastructure;

import static com.first.flash.climbing.problem.domain.QQueryProblem.queryProblem;

import com.first.flash.climbing.problem.domain.QueryProblem;
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

    public List<QueryProblem> findAll(final UUID lastId, final String sort, final int size,
        final List<String> difficulty, final List<String> sector, final Boolean hasSolution) {
        return queryFactory
            .selectFrom(queryProblem)
            .where(inSector(sector), inDifficulty(difficulty),
                hasSolution(hasSolution), ltId(lastId), notExpired())
            .orderBy(sortItem(sort))
            .orderBy(queryProblem.id.desc())
            .limit(size)
            .fetch();
    }

    private BooleanExpression notExpired() {
        return queryProblem.isExpired.eq(false);
    }

    private OrderSpecifier<?> sortItem(final String sort) {
        if (Objects.isNull(sort)) {
            return queryProblem.views.desc();
        }
        if (sort.equals("difficulty")) {
            return queryProblem.difficultyLevel.asc();
        }
        return queryProblem.views.desc();
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

    private BooleanExpression ltId(final UUID lastId) {
        if (Objects.isNull(lastId)) {
            return null;
        }
        return queryProblem.id.lt(lastId);
    }
}
