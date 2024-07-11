package com.first.flash.climbing.gym.application;

import static com.first.flash.climbing.gym.fixture.ClimbingGymFixture.createDefaultGymCreateRequestDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateRequestDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateResponseDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymResponseDto;
import com.first.flash.climbing.gym.domian.ClimbingGym;
import com.first.flash.climbing.gym.exception.exceptions.ClimbingGymNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClimbingGymServiceTest {

    @Autowired
    ClimbingGymService climbingGymService;

    @Test
    @Transactional
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
    @Transactional
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
    @Transactional
    void 클라이밍장_단건_검색_예외() {
        // when & then
        assertThatThrownBy(() -> climbingGymService.findClimbingGymById(100L))
            .isInstanceOf(ClimbingGymNotFoundException.class);
    }

    @Test
    @Transactional
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