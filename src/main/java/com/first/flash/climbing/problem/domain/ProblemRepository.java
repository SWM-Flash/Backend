package com.first.flash.climbing.problem.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProblemRepository {

    Problem save(final Problem problem);

    Optional<Problem> findById(final UUID id);

    List<QueryProblem> findAll(final UUID lastId, final String sort, final int size,
        final List<String> difficulty, final List<String> sector, final Boolean hasSolution);
}
