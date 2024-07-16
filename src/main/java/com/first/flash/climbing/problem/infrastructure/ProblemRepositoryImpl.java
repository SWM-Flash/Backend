package com.first.flash.climbing.problem.infrastructure;

import com.first.flash.climbing.problem.domain.Problem;
import com.first.flash.climbing.problem.domain.ProblemRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProblemRepositoryImpl implements ProblemRepository {

    private final ProblemJpaRepository jpaRepository;
    private final ProblemQueryDslRepository queryDslRepository;

    @Override
    public Problem save(final Problem problem) {
        return jpaRepository.save(problem);
    }

    @Override
    public Optional<Problem> findById(final UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public void expireProblemsBySectorIds(final List<Long> expiredSectorsIds) {
        queryDslRepository.expireProblemsBySectorIds(expiredSectorsIds);
    }
}
