package com.first.flash.climbing.problem.infrastructure;

import com.first.flash.climbing.problem.domain.QueryProblem;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryProblemJpaRepository extends JpaRepository<QueryProblem, UUID> {

    QueryProblem save(final QueryProblem queryProblem);

    Optional<QueryProblem> findById(final UUID id);
}
