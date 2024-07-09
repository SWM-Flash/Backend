package com.first.flash.climbing.sector.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.first.flash.climbing.sector.exception.exceptions.InvalidRemovalDateException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class SectorTest {

    private static final Long DEFAULT_GYM_ID = 1L;

    @Test
    void 탈거일_유효성_검사_예외_처리() {
        // when & then
        assertThatThrownBy(() -> Sector.createDefault("test", "test", LocalDate.now(),
            LocalDate.now().minusDays(1), DEFAULT_GYM_ID))
            .isInstanceOf(InvalidRemovalDateException.class);
    }
}
