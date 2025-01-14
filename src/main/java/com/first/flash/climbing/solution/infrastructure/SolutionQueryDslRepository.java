package com.first.flash.climbing.solution.infrastructure;

import static com.first.flash.climbing.gym.domian.QClimbingGym.climbingGym;
import static com.first.flash.climbing.problem.domain.QProblem.problem;
import static com.first.flash.climbing.problem.domain.QQueryProblem.queryProblem;
import static com.first.flash.climbing.solution.domain.QSolution.solution;
import static com.first.flash.climbing.solution.domain.QSolutionComment.solutionComment;

import com.first.flash.account.member.domain.Gender;
import com.first.flash.climbing.solution.application.dto.DifficultyDto;
import com.first.flash.climbing.solution.application.dto.MySolutionFilter;
import com.first.flash.climbing.solution.application.dto.UserSolutionGroupDto;
import com.first.flash.climbing.solution.domain.QSolution;
import com.first.flash.climbing.solution.infrastructure.dto.DetailSolutionDto;
import com.first.flash.climbing.solution.infrastructure.dto.SolutionRepositoryResponseDto;
import com.first.flash.climbing.solution.infrastructure.paging.SolutionCursor;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
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
                                  solution.uploaderDetail.uploaderId, solution.uploaderDetail.uploaderHeight,
                                  solution.uploaderDetail.uploaderReach, solution.uploaderDetail.uploaderGender,
                                  solution.uploaderDetail.profileImageUrl, solutionComment.count()
                              ))
                              .from(solution)
                              .leftJoin(solutionComment)
                              .on(solution.id.eq(solutionComment.solution.id))
                              .where(solution.problemId.eq(problemId)
                                                       .and(notInBlockedMembers(memberIds)))
                              .groupBy(solution.id)
                              .fetch();
    }

    public List<UserSolutionGroupDto> findByUploaderId(final UUID uploaderId,
        final MySolutionFilter mySolutionFilter, final SolutionCursor prevCursor, final int size) {
        List<Tuple> groupedData = jpaQueryFactory
            .select(
                climbingGym.id,
                climbingGym.gymName,
                solution.solutionDetail.solvedDate
            )
            .from(solution)
            .join(problem).on(solution.problemId.eq(problem.id))
            .join(climbingGym).on(problem.gymId.eq(climbingGym.id))
            .where(
                applyGymNameFilter(mySolutionFilter.gymInfoId()),
                applyYearMonthFilter(mySolutionFilter.year(), mySolutionFilter.month()),
                solution.uploaderDetail.uploaderId.eq(uploaderId),
                cursorCondition(prevCursor)
            )
            .groupBy(climbingGym.id, climbingGym.gymName, solution.solutionDetail.solvedDate)
            .orderBy(solution.solutionDetail.solvedDate.desc(), climbingGym.gymName.asc())
            .limit(size)
            .fetch();

        return getDtoFromGroupedData(groupedData, uploaderId);
    }

    private List<UserSolutionGroupDto> getDtoFromGroupedData(final List<Tuple> groupedData,
        final UUID uploaderId) {
        return groupedData.stream().map(group -> {
            Long gymId = group.get(climbingGym.id);
            String gymName = group.get(climbingGym.gymName);
            LocalDate solvedDate = group.get(solution.solutionDetail.solvedDate);

            List<Tuple> difficultiesWithThumbnails = jpaQueryFactory
                .select(
                    problem.difficultyInfo.difficultyName,
                    solution.id.count(),
                    solution.solutionDetail.thumbnailImageUrl
                )
                .from(solution)
                .join(problem).on(solution.problemId.eq(problem.id))
                .join(climbingGym).on(problem.gymId.eq(climbingGym.id))
                .where(
                    solution.uploaderDetail.uploaderId.eq(uploaderId),
                    climbingGym.gymName.eq(gymName),
                    solution.solutionDetail.solvedDate.eq(solvedDate)
                )
                .groupBy(problem.difficultyInfo.difficultyName)
                .orderBy(problem.difficultyInfo.level.desc())
                .fetch();

            List<DifficultyDto> difficulties = difficultiesWithThumbnails.stream()
                                                                         .map(
                                                                             tuple -> new DifficultyDto(
                                                                                 tuple.get(
                                                                                     problem.difficultyInfo.difficultyName),
                                                                                 tuple.get(
                                                                                     solution.id.count())
                                                                             ))
                                                                         .toList();

            String thumbnailImageUrl = difficultiesWithThumbnails.stream()
                                                                 .findFirst()
                                                                 .map(tuple -> tuple.get(
                                                                     solution.solutionDetail.thumbnailImageUrl))
                                                                 .orElse(null);

            return new UserSolutionGroupDto(
                gymId,
                gymName,
                difficulties,
                solvedDate,
                thumbnailImageUrl
            );
        }).toList();
    }

    public void updateUploaderInfo(final UUID uploaderId, final String nickName,
        final String instagramId, final String profileImageUrl, final Double uploaderHeight,
        final Double uploaderReach, final Gender uploaderGender) {
        jpaQueryFactory.update(solution)
                       .set(solution.uploaderDetail.uploader, nickName)
                       .set(solution.uploaderDetail.instagramId, instagramId)
                       .set(solution.uploaderDetail.profileImageUrl, profileImageUrl)
                       .set(solution.uploaderDetail.uploaderHeight, uploaderHeight)
                       .set(solution.uploaderDetail.uploaderReach, uploaderReach)
                       .set(solution.uploaderDetail.uploaderGender, uploaderGender)
                       .where(solution.uploaderDetail.uploaderId.eq(uploaderId))
                       .execute();
    }

    public List<DetailSolutionDto> findDetailSolutionGroupById(final UUID uploaderId,
        final Long gymId,
        final LocalDate solvedDate) {
        QSolution otherSolution = new QSolution("otherSolution");
        return jpaQueryFactory.select(Projections.constructor(DetailSolutionDto.class,
                                  solution.id, solution.solutionDetail.videoUrl, queryProblem.gymName,
                                  queryProblem.sectorName, queryProblem.id,
                                  solution.solutionDetail.review,
                                  queryProblem.difficultyName, solutionComment.count(),
                                  solution.solutionDetail.perceivedDifficulty,
                                  solution.solutionDetail.thumbnailImageUrl, queryProblem.holdColorCode,
                                  solution.solutionDetail.solvedDate,
                                  queryProblem.removalDate, queryProblem.settingDate, solution.createdAt,
                                  JPAExpressions.select(solution.id.count())
                                                .from(otherSolution)
                                                .where(
                                                    otherSolution.problemId.eq(solution.problemId),
                                                    otherSolution.id.ne(solution.id),
                                                    otherSolution.uploaderDetail.uploaderId.ne(
                                                        solution.uploaderDetail.uploaderId)
                                                )
                                                .gt((long) 0)
                                                .as("hasOtherSolutions")
                              ))
                              .from(solution)
                              .innerJoin(queryProblem)
                              .on(solution.problemId.eq(queryProblem.id))
                              .leftJoin(solutionComment)
                              .on(solution.id.eq(solutionComment.solution.id))
                              .where(
                                  solution.uploaderDetail.uploaderId.eq(uploaderId),
                                  solution.solutionDetail.solvedDate.eq(solvedDate),
                                  queryProblem.gymId.eq(gymId)
                              )
                              .groupBy(
                                  solution.id,
                                  solution.solutionDetail.videoUrl,
                                  queryProblem.gymName,
                                  queryProblem.sectorName,
                                  queryProblem.id,
                                  solution.solutionDetail.review,
                                  queryProblem.difficultyName,
                                  solution.solutionDetail.perceivedDifficulty,
                                  solution.solutionDetail.thumbnailImageUrl,
                                  queryProblem.holdColorCode,
                                  solution.solutionDetail.solvedDate,
                                  queryProblem.removalDate,
                                  queryProblem.settingDate,
                                  solution.createdAt
                              )
                              .fetch();
    }

    private BooleanExpression cursorCondition(final SolutionCursor prevSolutionCursor) {
        if (Objects.isNull(prevSolutionCursor) ||
            Objects.isNull(prevSolutionCursor.cursorValue())) {
            return null;
        }
        return solution.solutionDetail.solvedDate.lt(
                           LocalDate.parse(prevSolutionCursor.cursorValue()))
                                                 .or(solution.solutionDetail.solvedDate.eq(
                                                                 LocalDate.parse(
                                                                     prevSolutionCursor.cursorValue()))
                                                                                       .and(
                                                                                           climbingGym.gymName.lt(
                                                                                               prevSolutionCursor.gymName())));
    }

    private BooleanExpression applyGymNameFilter(final Long gymInfoId) {
        if (Objects.isNull(gymInfoId)) {
            return null;
        }
        return climbingGym.gymInfoId.eq(gymInfoId);
    }

    private BooleanExpression applyYearMonthFilter(final Integer yearFilter,
        final Integer monthFilter) {
        if (yearFilter == null && monthFilter == null) {
            return null;
        }
        BooleanExpression yearCondition =
            (yearFilter != null) ? solution.solutionDetail.solvedDate.year().eq(yearFilter) : null;
        BooleanExpression monthCondition =
            (monthFilter != null) ? solution.solutionDetail.solvedDate.month().eq(monthFilter)
                : null;

        return yearCondition != null && monthCondition != null
            ? yearCondition.and(monthCondition)
            : yearCondition != null ? yearCondition : monthCondition;
    }

    private BooleanExpression notInBlockedMembers(final List<UUID> memberIds) {
        if (memberIds.isEmpty()) {
            return null;
        }
        return solution.uploaderDetail.uploaderId.notIn(memberIds);
    }
}
