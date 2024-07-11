package com.first.flash.climbing.problem.domain;

import java.util.Optional;
import java.util.UUID;

public interface QueryProblemRepository {

    QueryProblem save(final QueryProblem queryProblem);

    Optional<QueryProblem> findById(final UUID id);
}
