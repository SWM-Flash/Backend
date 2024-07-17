package com.first.flash.climbing.problem.domain;

import static com.first.flash.climbing.problem.fixture.ProblemFixture.createDefault;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProblemTest {

    @Test
    void 조회수_증가() {
        // given
        Problem problem = createDefault();
        Integer prevViews = problem.getViews();

        // when
        problem.view();

        // then
        assertThat(problem.getViews()).isEqualTo(prevViews + 1);
    }
}
