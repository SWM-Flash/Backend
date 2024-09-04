package com.first.flash.climbing.solution.infrastructure;

import static com.first.flash.climbing.solution.domain.QSolution.solution;

import com.first.flash.climbing.solution.domain.Solution;
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
}
