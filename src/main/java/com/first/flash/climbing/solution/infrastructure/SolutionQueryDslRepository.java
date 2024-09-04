package com.first.flash.climbing.solution.infrastructure;

import static com.first.flash.climbing.problem.domain.QQueryProblem.queryProblem;
import static com.first.flash.climbing.solution.domain.QSolution.solution;

import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.infrastructure.dto.MySolutionDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SolutionQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Solution> findAllExcludedBlockedMembers(final UUID problemId,
        final List<UUID> memberIds) {
        return jpaQueryFactory.selectFrom(solution)
                              .where(solution.problemId.eq(problemId)
                                                       .and(solution.uploaderDetail.uploaderId
                                                           .notIn(memberIds))).fetch();
    }

    public List<MySolutionDto> findByUploaderId(final UUID uploaderId) {
        return jpaQueryFactory.select(Projections.constructor(MySolutionDto.class,
                                  queryProblem.gymName, queryProblem.sectorName, queryProblem.difficultyName,
                                  queryProblem.id, solution.id, solution.createdAt
                              ))
                              .from(solution)
                              .innerJoin(queryProblem)
                              .on(solution.problemId.eq(queryProblem.id))
                              .fetchJoin()
                              .where(solution.uploaderDetail.uploaderId.eq(uploaderId))
                              .fetch();
    }
}
