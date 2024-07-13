package com.first.flash.climbing.problem.infrastructure;

import com.first.flash.climbing.problem.domain.Problem;
import com.first.flash.climbing.problem.domain.ProblemRepository;
import com.first.flash.climbing.problem.domain.QueryProblem;
import com.first.flash.climbing.problem.infrastructure.paging.Cursor;
import com.first.flash.climbing.problem.infrastructure.paging.SortBy;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProblemRepositoryImpl implements ProblemRepository {

    private final ProblemJpaRepository jpaRepository;
    private final QueryProblemQueryDslRepository queryProblemQueryDslRepository;

    @Override
    public Problem save(final Problem problem) {
        return jpaRepository.save(problem);
    }

    @Override
    public Optional<Problem> findById(final UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<QueryProblem> findAll(final Cursor prevCursor, final SortBy sortBy, final int size,
        final Long gymId, final List<String> difficulty, final List<String> sector,
        final Boolean hasSolution) {
        return queryProblemQueryDslRepository.findAll(prevCursor, sortBy, size,
            gymId, difficulty, sector, hasSolution);
    }
}
