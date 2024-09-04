package com.first.flash.climbing.solution.infrastructure;

import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.SolutionRepository;
import com.first.flash.climbing.solution.infrastructure.dto.MySolutionDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SolutionRepositoryImpl implements SolutionRepository {

    private final SolutionJpaRepository solutionJpaRepository;
    private final SolutionQueryDslRepository solutionQueryDslRepository;

    @Override
    public Solution save(final Solution solution) {
        return solutionJpaRepository.save(solution);
    }

    @Override
    public Optional<Solution> findById(final Long id) {
        return solutionJpaRepository.findById(id);
    }

    @Override
    public List<Solution> findAllByProblemId(final UUID problemId,
        final List<UUID> blockedMembers) {
        if (blockedMembers.isEmpty()) {
            return solutionJpaRepository.findByProblemId(problemId);
        }
        return solutionQueryDslRepository.findAllExcludedBlockedMembers(
            problemId, blockedMembers);
    }

    @Override
    public List<MySolutionDto> findAllByUploaderId(final UUID uploaderId) {
        return solutionQueryDslRepository.findByUploaderId(uploaderId);
    }

    @Override
    public void deleteById(final Long id) {
        solutionJpaRepository.deleteById(id);
    }
}
