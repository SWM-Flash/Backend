package com.first.flash.climbing.problem.infrastructure;

import static com.first.flash.climbing.problem.domain.QProblem.problem;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProblemQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public void expireProblemsBySectorIds(final List<Long> expiredSectorsIds) {
        queryFactory.update(problem)
                    .set(problem.isExpired, true)
                    .where(problem.sectorId.in(expiredSectorsIds))
                    .execute();
    }
}
