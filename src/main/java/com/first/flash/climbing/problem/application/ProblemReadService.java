package com.first.flash.climbing.problem.application;

import static com.first.flash.climbing.problem.infrastructure.paging.SortBy.DIFFICULTY;
import static com.first.flash.climbing.problem.infrastructure.paging.SortBy.VIEWS;

import com.first.flash.climbing.problem.application.dto.ProblemDetailResponseDto;
import com.first.flash.climbing.problem.application.dto.ProblemsResponseDto;
import com.first.flash.climbing.problem.domain.Problem;
import com.first.flash.climbing.problem.domain.ProblemRepository;
import com.first.flash.climbing.problem.domain.QueryProblem;
import com.first.flash.climbing.problem.domain.QueryProblemRepository;
import com.first.flash.climbing.problem.exception.exceptions.ProblemNotFoundException;
import com.first.flash.climbing.problem.exception.exceptions.QueryProblemExpiredException;
import com.first.flash.climbing.problem.exception.exceptions.QueryProblemNotFoundException;
import com.first.flash.climbing.problem.infrastructure.paging.Cursor;
import com.first.flash.climbing.problem.infrastructure.paging.SortBy;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemReadService {

    private final QueryProblemRepository queryProblemRepository;
    private final ProblemRepository problemRepository;

    public ProblemsResponseDto findAll(final Long gymId, final String cursor,
        final String sortByRequest, final Integer size,
        final List<String> difficulty, final List<String> sector, final Boolean hasSolution) {
        Cursor prevCursor = Cursor.decode(cursor);
        SortBy sortBy = SortBy.from(sortByRequest);

        List<QueryProblem> queryProblems = queryProblemRepository.findAll(prevCursor, sortBy, size,
            gymId, difficulty, sector, hasSolution);
        String nextCursor = getNextCursor(sortBy, size, queryProblems);
        return ProblemsResponseDto.of(queryProblems, nextCursor);
    }

    @Transactional
    public ProblemDetailResponseDto viewProblems(final UUID problemId) {
        QueryProblem queryProblem = findQueryProblemById(problemId);
        Problem problem = findProblemById(problemId);
        validateExpiration(problem, queryProblem);
        problem.view();
        queryProblem.view();
        return ProblemDetailResponseDto.of(queryProblem);
    }

    public Problem findProblemById(final UUID problemId) {
        return problemRepository.findById(problemId).orElseThrow(
            () -> new ProblemNotFoundException(problemId));
    }

    public QueryProblem findQueryProblemById(final UUID problemId) {
        return queryProblemRepository.findById(problemId).orElseThrow(
            () -> new QueryProblemNotFoundException(problemId));
    }

    private void validateExpiration(final Problem problem, final QueryProblem queryProblem) {
        if (problem.isExpired() || queryProblem.isExpired()) {
            throw new QueryProblemExpiredException(problem.getId());
        }
    }

    private String getNextCursor(final SortBy sortBy, final Integer size,
        final List<QueryProblem> queryProblems) {
        if (hasNextCursor(size, queryProblems)) {
            return null;
        }
        QueryProblem lastProblem = queryProblems.get(size - 1);
        return new Cursor(sortBy, getLastSortValue(lastProblem, sortBy), lastProblem.getId())
            .encode();
    }

    private boolean hasNextCursor(final Integer size, final List<QueryProblem> queryProblems) {
        return queryProblems.size() != size;
    }

    private String getLastSortValue(final QueryProblem lastProblem, final SortBy sortBy) {
        if (sortBy.equals(DIFFICULTY)) {
            return lastProblem.getDifficultyLevel().toString();
        }
        if (sortBy.equals(VIEWS)) {
            return lastProblem.getViews().toString();
        }
        return lastProblem.getRecommendationValue().toString();
    }
}
