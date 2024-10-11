package com.first.flash.climbing.solution.infrastructure;

import static com.first.flash.climbing.problem.domain.QQueryProblem.queryProblem;
import static com.first.flash.climbing.solution.domain.QSolution.solution;

import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.infrastructure.dto.DetailSolutionDto;
import com.first.flash.climbing.solution.infrastructure.dto.MySolutionDto;
import com.first.flash.climbing.solution.infrastructure.paging.SolutionCursor;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    public List<MySolutionDto> findByUploaderId(final UUID uploaderId,
        final SolutionCursor prevSolutionCursor, final int size, final Long gymId,
        final List<String> difficulty) {
        return jpaQueryFactory.select(Projections.constructor(MySolutionDto.class,
                                  solution.id, queryProblem.gymName, queryProblem.sectorName,
                                  queryProblem.difficultyName, queryProblem.imageUrl, solution.createdAt
                              ))
                              .from(solution)
                              .innerJoin(queryProblem)
                              .on(solution.problemId.eq(queryProblem.id))
                              .fetchJoin()
                              .where(solution.uploaderDetail.uploaderId.eq(uploaderId),
                                  inGym(gymId), inDifficulties(difficulty),
                                  cursorCondition(prevSolutionCursor))
                              .orderBy(solution.createdAt.desc())
                              .limit(size)
                              .fetch();
    }

    public void updateUploaderInfo(final UUID uploaderId, final String nickName,
        final String instagramId, final String profileImageUrl) {
        jpaQueryFactory.update(solution)
                       .set(solution.uploaderDetail.uploader, nickName)
                       .set(solution.uploaderDetail.instagramId, instagramId)
                       .set(solution.uploaderDetail.profileImageUrl, profileImageUrl)
                       .where(solution.uploaderDetail.uploaderId.eq(uploaderId))
                       .execute();
    }

    public DetailSolutionDto findDetailSolutionById(final Long solutionId) {
        return jpaQueryFactory.select(Projections.constructor(DetailSolutionDto.class,
                                  solution.id, solution.solutionDetail.videoUrl, queryProblem.gymName,
                                  queryProblem.sectorName, solution.solutionDetail.review,
                                  queryProblem.difficultyName, solution.solutionDetail.perceivedDifficulty,
                                  queryProblem.removalDate, queryProblem.settingDate, solution.createdAt
                              ))
                              .from(solution)
                              .innerJoin(queryProblem)
                              .on(solution.problemId.eq(queryProblem.id))
                              .where(solution.id.eq(solutionId))
                              .fetchJoin()
                              .fetchOne();
    }

    private Predicate cursorCondition(final SolutionCursor prevSolutionCursor) {
        if (Objects.isNull(prevSolutionCursor) ||
            Objects.isNull(prevSolutionCursor.cursorValue())) {
            return null;
        }
        return solution.createdAt.before(LocalDateTime.parse(prevSolutionCursor.cursorValue()));
    }

    private BooleanExpression inGym(final Long gymId) {
        if (Objects.isNull(gymId)) {
            return null;
        }
        return queryProblem.gymId.eq(gymId);
    }

    private BooleanExpression inDifficulties(final List<String> difficulty) {
        if (Objects.isNull(difficulty)) {
            return null;
        }
        return queryProblem.difficultyName.in(difficulty);
    }
}
