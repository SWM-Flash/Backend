package com.first.flash.climbing.solution.infrastructure;

import static com.first.flash.climbing.gym.domian.QClimbingGym.climbingGym;
import static com.first.flash.climbing.problem.domain.QQueryProblem.queryProblem;
import static com.first.flash.climbing.solution.domain.QSolution.solution;

import com.first.flash.climbing.solution.domain.SolutionMetaDataFetcher;
import com.first.flash.climbing.solution.infrastructure.dto.SolutionMetaData;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SolutionMetaDataFetcherImpl implements SolutionMetaDataFetcher {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long getSolutionCountByGymInfoDifficultyName(final Long gymInfoId,
        final String difficultyName, final UUID memberId) {
        Long count = jpaQueryFactory
            .select(solution.id.count())
            .from(solution)
            .join(queryProblem).on(solution.problemId.eq(queryProblem.id))
            .join(climbingGym).on(queryProblem.gymId.eq(climbingGym.id))
            .where(
                climbingGym.gymInfoId.eq(gymInfoId),
                queryProblem.difficultyName.eq(difficultyName),
                solution.uploaderDetail.uploaderId.eq(memberId)
            )
            .fetchOne();

        if (Objects.isNull(count)) {
            return (long) 0;
        } else {
            return count;
        }
    }

    @Override
    public SolutionMetaData getSolutionMetaData(final Long id) {
        return jpaQueryFactory
            .select(Projections.constructor(SolutionMetaData.class,
                climbingGym.gymInfoId,
                queryProblem.difficultyName
            ))
            .from(solution)
            .join(queryProblem).on(solution.problemId.eq(queryProblem.id))
            .join(climbingGym).on(queryProblem.gymId.eq(climbingGym.id))
            .where(solution.id.eq(id))
            .fetchOne();
    }
}
