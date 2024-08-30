package com.first.flash.climbing.solution.application;

/*
class SolutionServiceTest {

    private final static UUID DEFAULT_PROBLEM_ID = UUID
        .fromString("0000-0000-0000-0000-0000");
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
*/