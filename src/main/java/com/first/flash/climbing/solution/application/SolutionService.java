package com.first.flash.climbing.solution.application;

import com.first.flash.climbing.problem.domain.ProblemIdConfirmEvent;
import com.first.flash.climbing.solution.application.dto.SolutionMetaResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionsResponseDto;
import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.SolutionRepository;
import com.first.flash.climbing.solution.domain.SolutionSavedEvent;
import com.first.flash.climbing.solution.domain.dto.SolutionCreateRequestDto;
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

    @Transactional
    public SolutionResponseDto saveSolution(final UUID problemId,
        final SolutionCreateRequestDto createRequestDto) {
        Solution solution = Solution.of(createRequestDto, problemId);
        Solution savedSolution = solutionRepository.save(solution);
        Events.raise(SolutionSavedEvent.of(savedSolution.getProblemId()));
        return SolutionResponseDto.toDto(savedSolution);
    }

    public Solution findSolutionById(final Long id) {
        return solutionRepository.findById(id)
                                 .orElseThrow(() -> new SolutionNotFoundException(id));
    }

    public SolutionsResponseDto findAllSolutionsByProblemId(final UUID problemId) {
        Events.raise(ProblemIdConfirmEvent.of(problemId));

        List<SolutionResponseDto> solutions = solutionRepository.findAllByProblemId(problemId)
                                                                .stream()
                                                                .map(SolutionResponseDto::toDto)
                                                                .toList();

        return new SolutionsResponseDto(solutions, new SolutionMetaResponseDto(solutions.size()));
    }
}
