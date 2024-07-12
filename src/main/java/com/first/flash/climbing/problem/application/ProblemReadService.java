package com.first.flash.climbing.problem.application;

import com.first.flash.climbing.problem.application.dto.ProblemsResponse;
import com.first.flash.climbing.problem.domain.ProblemRepository;
import com.first.flash.climbing.problem.domain.QueryProblem;
import com.first.flash.climbing.problem.infrastructure.dto.Cursor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemReadService {

    private final ProblemRepository problemRepository;

    public ProblemsResponse findAll(final String cursor, final String sortBy, final Integer size,
        final List<String> difficulty, final List<String> sector, final Boolean hasSolution) {
        Cursor prevCursor = Cursor.decode(cursor);
        List<QueryProblem> queryProblems = problemRepository.findAll(prevCursor, sortBy, size,
            difficulty, sector, hasSolution);
        String nextCursor = getNextCursor(sortBy, size, queryProblems);
        return ProblemsResponse.of(queryProblems, nextCursor);
    }

    private static String getNextCursor(final String sortBy, final Integer size,
        final List<QueryProblem> queryProblems) {
        if (queryProblems.size() != size) {
            return null;
        }
        QueryProblem lastProblem = queryProblems.get(size - 1);
        return new Cursor(sortBy, getLastSortValue(lastProblem, sortBy),
            lastProblem.getId()).encode();
    }

    private static String getLastSortValue(final QueryProblem lastProblem, final String sortBy) {
        if (sortBy.equals("views")) {
            return lastProblem.getId().toString();
        }
        if (sortBy.equals("difficulty")) {
            return lastProblem.getDifficultyLevel().toString();
        }
        return lastProblem.getViews().toString();
    }
}
