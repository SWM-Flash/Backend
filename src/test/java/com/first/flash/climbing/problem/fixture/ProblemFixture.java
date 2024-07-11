package com.first.flash.climbing.problem.fixture;

import com.first.flash.climbing.problem.domain.Problem;

public class ProblemFixture {

    private static final Integer DEFAULT_DIFFICULTY_LEVEL = 1;
    private static final Long DEFAULT_GYM_ID = 1L;
    private static final Long DEFAULT_SECTOR_ID = 1L;

    public static Problem createDefault() {
        return Problem.createDefault("example.com", false, "difficultyName",
            DEFAULT_DIFFICULTY_LEVEL, DEFAULT_GYM_ID, DEFAULT_SECTOR_ID);
    }

}
