package com.first.flash.climbing.problem.domain;

import com.first.flash.climbing.problem.infrastructure.dto.Cursor;
import com.first.flash.climbing.problem.infrastructure.dto.SortBy;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProblemRepository {

    Problem save(final Problem problem);

    Optional<Problem> findById(final UUID id);

    List<QueryProblem> findAll(final Cursor preCursor, final SortBy sortBy, final int size,
        final List<String> difficulty, final List<String> sector, final Boolean hasSolution);
}
