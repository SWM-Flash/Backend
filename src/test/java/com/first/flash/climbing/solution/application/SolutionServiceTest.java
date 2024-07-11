package com.first.flash.climbing.solution.application;

import static com.first.flash.climbing.solution.fixture.SolutionFixture.createDefaultRequestDto;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.first.flash.climbing.solution.application.dto.SolutionResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionsResponseDto;
import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.SolutionRepository;
import com.first.flash.climbing.solution.domain.dto.SolutionCreateRequestDto;
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
        });
    }

    @Test
    void 아이디로_해설_찾기_성공() {
        // given
        SolutionCreateRequestDto createRequestDto = createDefaultRequestDto();
        SolutionResponseDto saveDto = solutionService.saveSolution(DEFAULT_PROBLEM_ID,
            createRequestDto);

        // when
        Solution foundSolution = solutionService.findSolutionById(saveDto.id());

        // then
        assertSoftly(softly -> {
            softly.assertThat(foundSolution).isNotNull();
            softly.assertThat(foundSolution.getProblemId()).isEqualTo(DEFAULT_PROBLEM_ID);
        });
    }

    @Test
    void 아이디로_해설_찾기_실패() {
        // when & then
        assertThatThrownBy(() -> solutionService.findSolutionById(NON_EXISTENT_SOLUTION_ID))
            .isInstanceOf(SolutionNotFoundException.class);
    }

    @Test
    void 문제_ID로_해설_목록_찾기() {
        // given
        SolutionCreateRequestDto createRequestDto1 = createDefaultRequestDto();
        SolutionCreateRequestDto createRequestDto2 = createDefaultRequestDto();
        solutionService.saveSolution(DEFAULT_PROBLEM_ID, createRequestDto1);
        solutionService.saveSolution(DEFAULT_PROBLEM_ID, createRequestDto2);

        // when
        SolutionsResponseDto solutionsResponse = solutionService.findAllSolutionsByProblemId(
            DEFAULT_PROBLEM_ID);

        // then
        assertSoftly(softly -> {
            softly.assertThat(solutionsResponse).isNotNull();
            softly.assertThat(solutionsResponse.solutions()).hasSize(2);
            softly.assertThat(solutionsResponse.meta().count()).isEqualTo(2);
        });
    }
}