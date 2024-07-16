package com.first.flash.climbing.solution.infrastructure;

import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.SolutionRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SolutionRepositoryImpl implements SolutionRepository {

    private final SolutionJpaRepository solutionJpaRepository;

    @Override
    public Solution save(final Solution solution) {
        return solutionJpaRepository.save(solution);
    }

    @Override
    public Optional<Solution> findById(final Long id) {
        return solutionJpaRepository.findById(id);
    }

    @Override
    public List<Solution> findAllByProblemId(final UUID problemId) {
        return solutionJpaRepository.findByProblemId(problemId);
    }
}
