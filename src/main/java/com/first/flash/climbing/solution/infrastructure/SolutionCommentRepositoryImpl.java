package com.first.flash.climbing.solution.infrastructure;

import com.first.flash.climbing.solution.domain.SolutionComment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SolutionCommentRepositoryImpl implements SolutionCommentRepository {

    private final SolutionCommentJpaRepository solutionCommentJpaRepository;
    private final SolutionCommentQueryDslRepository solutionCommentQueryDslRepository;

    @Override
    public SolutionComment save(final SolutionComment solutionComment) {
        return solutionCommentJpaRepository.save(solutionComment);
    }

    @Override
    public Optional<SolutionComment> findById(final Long id) {
        return solutionCommentJpaRepository.findById(id);
    }

    @Override
    public List<SolutionComment> findBySolutionId(final Long solutionId) {
        return solutionCommentJpaRepository.findBySolutionId(solutionId);
    }

    @Override
    public void deleteByCommenterId(final UUID commenterId) {
        solutionCommentJpaRepository.deleteByCommenterDetailCommenterId(commenterId);
    }

    @Override
    public void updateCommenterInfo(final UUID commenterId, final String nickName,
        final String profileImageUrl) {
        solutionCommentQueryDslRepository.updateCommenterInfo(commenterId, nickName, profileImageUrl);
    }

    @Override
    public void delete(final SolutionComment comment) {
        solutionCommentJpaRepository.delete(comment);
    }
}
