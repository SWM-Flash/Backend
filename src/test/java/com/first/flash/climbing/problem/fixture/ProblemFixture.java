package com.first.flash.climbing.problem.fixture;

import com.first.flash.climbing.problem.domain.Problem;
import java.util.UUID;

public class ProblemFixture {

    private static final Integer DEFAULT_DIFFICULTY_LEVEL = 1;
    private static final Long DEFAULT_GYM_ID = 1L;
    private static final Long DEFAULT_SECTOR_ID = 1L;

    public static Problem createDefault() {
        return Problem.createDefault(UUID.randomUUID(), "example.com", "difficultyName",
            DEFAULT_DIFFICULTY_LEVEL, DEFAULT_GYM_ID, DEFAULT_SECTOR_ID);
    }

}
