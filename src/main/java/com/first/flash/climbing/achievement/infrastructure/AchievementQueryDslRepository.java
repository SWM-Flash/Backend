package com.first.flash.climbing.achievement.infrastructure;

import static com.first.flash.climbing.gym.domian.QClimbingGym.climbingGym;
import static com.first.flash.climbing.problem.domain.QQueryProblem.queryProblem;
import static com.first.flash.climbing.solution.domain.QSolution.solution;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AchievementQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public long findSolutionCountByGymNameDifficulty(final Long gymInfoId,
        final String difficultyName, final UUID memberId) {
        Long count = queryFactory
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
            return 0;
        } else {
            return count;
        }
    }
}
