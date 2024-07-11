package com.first.flash.climbing.solution.fixture;

import com.first.flash.climbing.solution.domain.dto.SolutionCreateRequestDto;

public class SolutionFixture {

    public static SolutionCreateRequestDto createDefaultRequestDto() {
        return new SolutionCreateRequestDto("default_url.mp4", "uploader",
            "review content", "@instagram_id");
    }
}
