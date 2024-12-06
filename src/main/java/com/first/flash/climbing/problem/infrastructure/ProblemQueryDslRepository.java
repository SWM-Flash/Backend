package com.first.flash.climbing.problem.infrastructure;

import static com.first.flash.climbing.problem.domain.QProblem.problem;

import com.first.flash.climbing.problem.infrastructure.dto.ThumbnailSolutionDto;
import com.first.flash.climbing.solution.domain.QSolution;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
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

    public ThumbnailSolutionDto findNextSolution(UUID problemId) {
        QSolution solution = QSolution.solution;

        return queryFactory.select(Projections.constructor(ThumbnailSolutionDto.class,
                               solution.id,
                               solution.uploaderDetail.uploader,
                               solution.solutionDetail.thumbnailImageUrl))
                           .from(solution)
                           .where(solution.problemId.eq(problemId))
                           .orderBy(solution.createdAt.asc())
                           .limit(1)
                           .fetchOne();
    }
}
