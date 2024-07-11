package com.first.flash.climbing.problem.infrastructure;

import com.first.flash.climbing.problem.domain.QueryProblem;
import com.first.flash.climbing.problem.domain.QueryProblemRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueryProblemRepositoryImpl implements QueryProblemRepository {

    private final QueryProblemJpaRepository jpaRepository;

    @Override
    public QueryProblem save(final QueryProblem queryProblem) {
        return jpaRepository.save(queryProblem);
    }

    @Override
    public Optional<QueryProblem> findById(final UUID id) {
        return jpaRepository.findById(id);
    }
}
