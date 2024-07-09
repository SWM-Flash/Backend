package com.first.flash.climbing.gym.domian;

import static com.first.flash.climbing.gym.fixture.ClimbingGymFixture.createDefaultGym;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.first.flash.climbing.gym.exception.exceptions.DifficultyNotFoundException;
import org.junit.jupiter.api.Test;

class ClimbingGymTest {

    private final static String DEFAULT_CLIMBING_DIFFICULTY = "빨강";

    @Test
    void 난이도_이름_검증() {
        // given
        ClimbingGym gym = createDefaultGym();

        // when & them
        assertDoesNotThrow(() -> gym.getDifficultyByName(DEFAULT_CLIMBING_DIFFICULTY));
    }

    @Test
    void 난이도_이름_검증_예외() {
        // given
        ClimbingGym gym = createDefaultGym();

        // when & then
        assertThatThrownBy(() -> gym.getDifficultyByName("invalid difficulty"))
            .isInstanceOf(DifficultyNotFoundException.class);
    }
}