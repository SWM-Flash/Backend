package com.first.flash.climbing.solution.application;

import com.first.flash.account.member.application.BlockService;
import com.first.flash.climbing.gym.domian.ClimbingGymIdConfirmRequestedEvent;
import com.first.flash.climbing.problem.domain.ProblemIdConfirmRequestedEvent;
import com.first.flash.climbing.solution.application.dto.MySolutionsResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionUpdateRequestDto;
import com.first.flash.climbing.solution.application.dto.SolutionsPageResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionsResponseDto;
import com.first.flash.climbing.solution.domain.PerceivedDifficulty;
import com.first.flash.climbing.solution.domain.PerceivedDifficultySetEvent;
import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.SolutionDeletedEvent;
import com.first.flash.climbing.solution.domain.SolutionRepository;
import com.first.flash.climbing.solution.domain.SolutionSavedEvent;
import com.first.flash.climbing.solution.exception.exceptions.SolutionAccessDeniedException;
import com.first.flash.climbing.solution.exception.exceptions.SolutionNotFoundException;
import com.first.flash.climbing.solution.infrastructure.dto.DetailSolutionDto;
import com.first.flash.climbing.solution.infrastructure.dto.MySolutionDto;
import com.first.flash.climbing.solution.infrastructure.paging.SolutionCursor;
import com.first.flash.global.event.Events;
import com.first.flash.global.util.AuthUtil;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SolutionService {

    private final SolutionRepository solutionRepository;
    private final BlockService blockService;

    public Solution findSolutionById(final Long id) {
        return solutionRepository.findById(id)
                                 .orElseThrow(() -> new SolutionNotFoundException(id));
    }

    public DetailSolutionDto findDetailSolutionById(final Long solutionId) {
        return solutionRepository.findDetailSolutionById(solutionId);
    }

    public SolutionsResponseDto findAllSolutionsByProblemId(final UUID problemId) {
        Events.raise(ProblemIdConfirmRequestedEvent.of(problemId));
        List<UUID> blockedMembers = blockService.findBlockedMembers();
        List<SolutionResponseDto> solutions = solutionRepository.findAllByProblemId(problemId,
                                                                    blockedMembers)
                                                                .stream()
                                                                .map(SolutionResponseDto::toDto)
                                                                .toList();

        return SolutionsResponseDto.of(solutions);
    }

    public SolutionsPageResponseDto findMySolutions(final String cursor, final int size,
        final Long gymId, final List<String> difficulty) {
        UUID myId = AuthUtil.getId();
        if (!Objects.isNull(gymId)) {
            Events.raise(ClimbingGymIdConfirmRequestedEvent.of(gymId));
        }
        SolutionCursor prevSolutionCursor = SolutionCursor.decode(cursor);
        List<MySolutionDto> solutions = solutionRepository.findMySolutions(myId, prevSolutionCursor,
            size, gymId, difficulty);
        String nextCursor = getNextCursor(size, solutions);
        return SolutionsPageResponseDto.of(solutions, nextCursor);
    }

    @Transactional
    public SolutionResponseDto updateContent(final Long id,
        final SolutionUpdateRequestDto requestDto) {

        Solution solution = solutionRepository.findById(id)
                                              .orElseThrow(() -> new SolutionNotFoundException(id));
        validateUploader(solution);

        Integer newPerceivedDifficulty = requestDto.perceivedDifficulty().getValue();
        Integer oldPerceivedDifficulty = solution.getSolutionDetail().getPerceivedDifficulty();
        int difficultyDifference = calculateDifficultyDifference(oldPerceivedDifficulty, newPerceivedDifficulty);

        solution.updateContentInfo(requestDto.review(), requestDto.videoUrl(), newPerceivedDifficulty);

        Events.raise(PerceivedDifficultySetEvent.of(
            solution.getProblemId(),
            difficultyDifference
        ));

        return SolutionResponseDto.toDto(solution);
    }

    @Transactional
    public void deleteSolution(final Long id) {
        Solution solution = solutionRepository.findById(id)
                                              .orElseThrow(() -> new SolutionNotFoundException(id));
        validateUploader(solution);
        solutionRepository.deleteById(id);

        Integer perceivedDifficulty = solution.getSolutionDetail().getPerceivedDifficulty();
        Events.raise(SolutionDeletedEvent.of(solution.getProblemId(), perceivedDifficulty));
    }

    @Transactional
    public void deleteByUploaderId(final UUID memberId) {
        solutionRepository.deleteByUploaderId(memberId);
    }

    private String getNextCursor(final int size, final List<MySolutionDto> solutions) {
        if (hasNextCursor(size, solutions)) {
            return null;
        }
        MySolutionDto lastSolution = solutions.get(solutions.size() - 1);
        return new SolutionCursor(lastSolution.uploadedAt().toString(),
            lastSolution.solutionId()).encode();
    }

    private boolean hasNextCursor(final int size, final List<MySolutionDto> solutions) {
        return solutions.size() != size;
    }

    private void validateUploader(final Solution solution) {
        UUID uploaderId = solution.getUploaderDetail().getUploaderId();
        if (!AuthUtil.isSameId(uploaderId)) {
            throw new SolutionAccessDeniedException();
        }
    }

    private int calculateDifficultyDifference(Integer oldPerceivedDifficulty, Integer newPerceivedDifficulty) {
        return newPerceivedDifficulty - oldPerceivedDifficulty;
    }
}
