package com.first.flash.climbing.solution.infrastructure;

import static com.first.flash.climbing.problem.domain.QQueryProblem.queryProblem;
import static com.first.flash.climbing.solution.domain.QSolution.solution;
import static com.first.flash.climbing.solution.domain.QSolutionComment.solutionComment;

import com.first.flash.climbing.solution.infrastructure.dto.DetailSolutionDto;
import com.first.flash.climbing.solution.infrastructure.dto.MySolutionDto;
import com.first.flash.climbing.solution.infrastructure.dto.SolutionRepositoryResponseDto;
import com.first.flash.climbing.solution.infrastructure.paging.SolutionCursor;
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

    public List<SolutionRepositoryResponseDto> findAllExcludedBlockedMembers(final UUID problemId,
        final List<UUID> memberIds) {
        return jpaQueryFactory.select(Projections.constructor(SolutionRepositoryResponseDto.class,
                                  solution.id, solution.uploaderDetail.uploader, solution.solutionDetail.review,
                                  solution.uploaderDetail.instagramId, solution.solutionDetail.videoUrl,
                                  solution.uploaderDetail.uploaderId, solution.uploaderDetail.profileImageUrl,
                                  solutionComment.count()
                              ))
                              .from(solution)
                              .leftJoin(solutionComment)
                              .on(solution.id.eq(solutionComment.solution.id))
                              .where(solution.problemId.eq(problemId)
                                                       .and(notInBlockedMembers(memberIds)))
                              .groupBy(solution.id)
                              .fetch();
    }

    public List<MySolutionDto> findByUploaderId(final UUID uploaderId,
        final SolutionCursor prevSolutionCursor, final int size, final Long gymId,
        final List<String> difficulty) {
        return jpaQueryFactory.select(Projections.constructor(MySolutionDto.class,
                                  solution.id, queryProblem.gymName, queryProblem.sectorName,
                                  queryProblem.difficultyName, queryProblem.imageUrl, solutionComment.count(),
                                  solution.createdAt
                              ))
                              .from(solution)
                              .innerJoin(queryProblem)
                              .on(solution.problemId.eq(queryProblem.id))
                              .leftJoin(solutionComment)
                              .on(solution.id.eq(solutionComment.solution.id))
                              .where(solution.uploaderDetail.uploaderId.eq(uploaderId),
                                  inGym(gymId), inDifficulties(difficulty),
                                  cursorCondition(prevSolutionCursor))
                              .groupBy(solution.id)
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
                                  queryProblem.sectorName, solution.solutionDetail.review, queryProblem.difficultyName,
                                  solutionComment.count(), queryProblem.removalDate, queryProblem.settingDate,
                                  solution.createdAt))
                              .from(solution)
                              .innerJoin(queryProblem)
                              .on(solution.problemId.eq(queryProblem.id))
                              .leftJoin(solutionComment)
                              .on(solution.id.eq(solutionComment.solution.id))
                              .where(solution.id.eq(solutionId))
                              .groupBy(solution.id)
                              .fetchOne();
    }

    private BooleanExpression cursorCondition(final SolutionCursor prevSolutionCursor) {
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

    private BooleanExpression notInBlockedMembers(final List<UUID> memberIds) {
        if (memberIds.isEmpty()) {
            return null;
        }
        return solution.uploaderDetail.uploaderId.notIn(memberIds);
    }
}
