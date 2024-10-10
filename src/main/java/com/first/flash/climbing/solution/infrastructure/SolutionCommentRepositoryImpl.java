package com.first.flash.climbing.solution.infrastructure;

import com.first.flash.climbing.solution.domain.SolutionComment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SolutionCommentRepositoryImpl implements SolutionCommentRepository {

    private final SolutionCommentJpaRepository solutionCommentJpaRepository;

    @Override
    public SolutionComment save(final SolutionComment solutionComment) {
        return solutionCommentJpaRepository.save(solutionComment);
    }

    @Override
    public List<SolutionComment> findBySolutionId(final Long solutionId) {
        return solutionCommentJpaRepository.findBySolutionId(solutionId);
    }
}
