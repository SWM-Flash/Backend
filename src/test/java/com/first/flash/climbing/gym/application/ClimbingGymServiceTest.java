package com.first.flash.climbing.gym.application;

import static com.first.flash.climbing.gym.fixture.ClimbingGymFixture.createDefaultGymCreateRequestDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateRequestDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateResponseDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymResponseDto;
import com.first.flash.climbing.gym.domian.ClimbingGym;
import com.first.flash.climbing.gym.domian.ClimbingGymRepository;
import com.first.flash.climbing.gym.exception.exceptions.ClimbingGymNotFoundException;
import com.first.flash.climbing.gym.infrastructure.FakeClimbingGymRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClimbingGymServiceTest {

    ClimbingGymRepository climbingGymRepository;
    ClimbingGymService climbingGymService;

    @BeforeEach
    void init() {
        climbingGymRepository = new FakeClimbingGymRepository();
        climbingGymService = new ClimbingGymService(climbingGymRepository);
    }

    @Test
    void 클라이밍장_저장() {
        // given
        ClimbingGymCreateRequestDto createDto = createDefaultGymCreateRequestDto();

        // when
        ClimbingGymCreateResponseDto saveDto = climbingGymService.save(createDto);
        ClimbingGym foundGym = climbingGymService.findClimbingGymById(saveDto.id());

        // then
        assertSoftly(softly -> {
            softly.assertThat(foundGym).isNotNull();
            softly.assertThat(foundGym.getDifficulties()).isNotEmpty();
        });
    }

    @Test
    void 클라이밍장_단건_검색() {
        // given
        ClimbingGymCreateRequestDto createDto = createDefaultGymCreateRequestDto();

        // when
        ClimbingGymCreateResponseDto saveDto = climbingGymService.save(createDto);
        ClimbingGym foundGym = climbingGymService.findClimbingGymById(saveDto.id());

        // then
        assertThat(foundGym).isNotNull();
    }

    @Test
    void 클라이밍장_단건_검색_예외() {
        // when & then
        assertThatThrownBy(() -> climbingGymService.findClimbingGymById(100L))
            .isInstanceOf(ClimbingGymNotFoundException.class);
    }

    @Test
    void 클라이밍장_다건_검색() {
        // given
        ClimbingGymCreateRequestDto createDto1 = createDefaultGymCreateRequestDto();
        ClimbingGymCreateRequestDto createDto2 = createDefaultGymCreateRequestDto();
        climbingGymService.save(createDto1);
        climbingGymService.save(createDto2);

        // when
        List<ClimbingGymResponseDto> gyms = climbingGymService.findAllClimbingGyms();

        // then
        assertSoftly(softly -> {
            softly.assertThat(gyms).isNotEmpty();
            softly.assertThat(gyms.size()).isEqualTo(2);
        });
    }
}