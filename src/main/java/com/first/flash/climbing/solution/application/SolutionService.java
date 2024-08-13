package com.first.flash.climbing.solution.application;

import com.first.flash.climbing.problem.domain.ProblemIdConfirmRequestedEvent;
import com.first.flash.climbing.solution.application.dto.SolutionMetaResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionUpdateRequestDto;
import com.first.flash.climbing.solution.application.dto.SolutionsResponseDto;
import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.SolutionRepository;
import com.first.flash.climbing.solution.exception.exceptions.SolutionNotFoundException;
import com.first.flash.global.event.Events;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SolutionService {

    private final SolutionRepository solutionRepository;

    public Solution findSolutionById(final Long id) {
        return solutionRepository.findById(id)
                                 .orElseThrow(() -> new SolutionNotFoundException(id));
    }

    public SolutionsResponseDto findAllSolutionsByProblemId(final UUID problemId) {
        Events.raise(ProblemIdConfirmRequestedEvent.of(problemId));

        List<SolutionResponseDto> solutions = solutionRepository.findAllByProblemId(problemId)
                                                                .stream()
                                                                .map(SolutionResponseDto::toDto)
                                                                .toList();

        return new SolutionsResponseDto(solutions, new SolutionMetaResponseDto(solutions.size()));
    }

    public SolutionsResponseDto findAllSolutionsByMemberId(final UUID memberId) {
        List<SolutionResponseDto> solutions = solutionRepository.findAllByMemberId(memberId)
                                                                .stream()
                                                                .map(SolutionResponseDto::toDto)
                                                                .toList();

        return new SolutionsResponseDto(solutions, new SolutionMetaResponseDto(solutions.size()));
    }

    @Transactional
    public SolutionResponseDto updateContent(final Long id,
        final SolutionUpdateRequestDto requestDto) {

        Solution solution = solutionRepository.findById(id)
                                              .orElseThrow(() -> new SolutionNotFoundException(id));
        solution.updateContentInfo(requestDto.review(), requestDto.videoUrl());

        return SolutionResponseDto.toDto(solution);
    }

    @Transactional
    public void deleteSolution(final Long id, final UUID problemId) {
        Events.raise(ProblemIdConfirmRequestedEvent.of(problemId));

        solutionRepository.deleteById(id);
    }
}
