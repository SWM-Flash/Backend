package com.first.flash.climbing.solution.infrastructure;

import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.SolutionRepository;
import com.first.flash.climbing.solution.infrastructure.dto.DetailSolutionDto;
import com.first.flash.climbing.solution.infrastructure.dto.MySolutionDto;
import com.first.flash.climbing.solution.infrastructure.dto.SolutionRepositoryResponseDto;
import com.first.flash.climbing.solution.infrastructure.paging.SolutionCursor;
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
    public List<SolutionRepositoryResponseDto> findAllByProblemId(final UUID problemId,
        final List<UUID> blockedMembers) {
        return solutionQueryDslRepository.findAllExcludedBlockedMembers(
            problemId, blockedMembers);
    }

    @Override
    public void deleteById(final Long id) {
        solutionJpaRepository.deleteById(id);
    }

    @Override
    public void updateUploaderInfo(final UUID uploaderId, final String nickName,
        final String instagramId, final String profileImageUrl) {
        solutionQueryDslRepository.updateUploaderInfo(uploaderId, nickName, instagramId,
            profileImageUrl);
    }

    @Override
    public DetailSolutionDto findDetailSolutionById(final Long solutionId) {
        return solutionQueryDslRepository.findDetailSolutionById(solutionId);
    }

    @Override
    public void deleteByUploaderId(final UUID memberId) {
        solutionJpaRepository.deleteByUploaderDetail_UploaderId(memberId);
    }

    @Override
    public List<MySolutionDto> findMySolutions(final UUID myId,
        final SolutionCursor prevSolutionCursor,
        final int size, final Long gymId, final List<String> difficulty) {
        return solutionQueryDslRepository.findByUploaderId(myId, prevSolutionCursor, size, gymId,
            difficulty);
    }
}
