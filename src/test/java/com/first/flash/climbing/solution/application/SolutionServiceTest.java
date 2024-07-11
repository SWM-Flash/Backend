package com.first.flash.climbing.solution.application;

import static com.first.flash.climbing.solution.fixture.SolutionFixture.createDefaultRequestDto;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.SolutionRepository;
import com.first.flash.climbing.solution.domain.dto.SolutionCreateRequestDto;
import com.first.flash.climbing.solution.domain.dto.SolutionResponseDto;
import com.first.flash.climbing.solution.exception.exceptions.SolutionNotFoundException;
import com.first.flash.climbing.solution.infrastructure.FakeSolutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolutionServiceTest {

    private final static Long DEFAULT_PROBLEM_ID = 1L;
    private final static Long NON_EXISTENT_SOLUTION_ID = 999L;

    private SolutionRepository solutionRepository;
    private SolutionService solutionService;

    @BeforeEach
    void init() {
        solutionRepository = new FakeSolutionRepository();
        solutionService = new SolutionService(solutionRepository);
    }

    @Test
    void 해설_저장() {
        // given
        SolutionCreateRequestDto createRequestDto = createDefaultRequestDto();

        // when
        SolutionResponseDto saveDto = solutionService.saveSolution(DEFAULT_PROBLEM_ID,
            createRequestDto);

        // then
        assertSoftly(softly -> {
            softly.assertThat(saveDto).isNotNull();
            softly.assertThat(saveDto.id()).isNotNull();
            softly.assertThat(saveDto.instagramId()).isEqualTo(createRequestDto.instagramId());
        });
    }

    @Test
    void 아이디로_해설_찾기_성공() {
        // given
        SolutionCreateRequestDto createRequestDto = createDefaultRequestDto();
        SolutionResponseDto saveDto = solutionService.saveSolution(DEFAULT_PROBLEM_ID,
            createRequestDto);

        // when
        Solution foundSolution = solutionService.findById(saveDto.id());

        // then
        assertSoftly(softly -> {
            softly.assertThat(foundSolution).isNotNull();
            softly.assertThat(foundSolution.getInstagramId())
                .isEqualTo(createRequestDto.instagramId());
            softly.assertThat(foundSolution.getProblemId()).isEqualTo(DEFAULT_PROBLEM_ID);
        });
    }

    @Test
    void 아이디로_해설_찾기_실패() {
        // when & then
        assertThatThrownBy(() -> solutionService.findById(NON_EXISTENT_SOLUTION_ID))
            .isInstanceOf(SolutionNotFoundException.class);
    }
}