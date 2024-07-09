package com.first.flash.climbing.sector.domain;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void 탈거일_업데이트() {
        // given
        Sector sector = Sector.createExceptRemovalDate("test", "test", LocalDate.now(),
            DEFAULT_GYM_ID);

        // when
        sector.updateRemovalDate(LocalDate.now().plusDays(1));

        // then
        LocalDate removalDate = sector.getRemovalDate();
        assertThat(removalDate).isEqualTo(LocalDate.now().plusDays(1));
    }

    @Test
    void 탈거일_수정_예외처리() {
        // given
        Sector sector = Sector.createExceptRemovalDate("test", "test", LocalDate.now(),
            DEFAULT_GYM_ID);

        // when & then
        assertThatThrownBy(() -> sector.updateRemovalDate(LocalDate.now().minusDays(1)))
            .isInstanceOf(InvalidRemovalDateException.class);
    }
}
