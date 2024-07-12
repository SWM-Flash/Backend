package com.first.flash.climbing.problem.application;

import static com.first.flash.climbing.problem.infrastructure.dto.SortBy.DIFFICULTY;

import com.first.flash.climbing.problem.application.dto.ProblemsResponse;
import com.first.flash.climbing.problem.domain.ProblemRepository;
import com.first.flash.climbing.problem.domain.QueryProblem;
import com.first.flash.climbing.problem.infrastructure.dto.Cursor;
import com.first.flash.climbing.problem.infrastructure.dto.SortBy;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemReadService {

    private final ProblemRepository problemRepository;

    public ProblemsResponse findAll(final String cursor,
        final String sortByRequest, final Integer size,
        final List<String> difficulty, final List<String> sector, final Boolean hasSolution) {
        Cursor prevCursor = Cursor.decode(cursor);
        SortBy sortBy = SortBy.from(sortByRequest);

        List<QueryProblem> queryProblems = problemRepository.findAll(prevCursor, sortBy, size,
            difficulty, sector, hasSolution);
        String nextCursor = getNextCursor(sortBy, size, queryProblems);
        return ProblemsResponse.of(queryProblems, nextCursor);
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
        return lastProblem.getViews().toString();
    }
}
