package com.first.flash.climbing.solution.application;

import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.SolutionRepository;
import com.first.flash.climbing.solution.domain.dto.SolutionCreateRequestDto;
import com.first.flash.climbing.solution.domain.dto.SolutionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SolutionService {

    private final SolutionRepository solutionRepository;

    @Transactional
    public SolutionResponseDto saveSolution(final Long problemId,
        final SolutionCreateRequestDto createRequestDto) {
        Solution solution = Solution.of(createRequestDto, problemId);
        return SolutionResponseDto.toDto(solutionRepository.save(solution));
    }
}
