package com.first.flash.climbing.sector.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.first.flash.climbing.sector.exception.exceptions.InvalidRemovalDateException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class SectorTest {

    private static final String DEFAULT_SECTOR_IMAGE = "example image url";
    private static final Long DEFAULT_GYM_ID = 1L;

    @Test
    void 탈거일_유효성_검사_예외_처리() {
        // given
        LocalDate settingDate = LocalDate.now();

        // when & then
        assertThatThrownBy(() -> Sector.createDefault("test", "test", settingDate,
            settingDate.minusDays(1), DEFAULT_GYM_ID, DEFAULT_SECTOR_IMAGE))
            .isInstanceOf(InvalidRemovalDateException.class);
    }

    @Test
    void 탈거일_업데이트() {
        // given
        LocalDate settingDate = LocalDate.now();
        Sector sector = Sector.createExceptRemovalDate("test", "test", settingDate,
            DEFAULT_GYM_ID, DEFAULT_SECTOR_IMAGE);

        // when
        sector.updateRemovalDate(settingDate.plusDays(1));

        // then
        LocalDate removalDate = sector.getRemovalDate();
        assertThat(removalDate).isEqualTo(settingDate.plusDays(1));
    }

    @Test
    void 탈거일_수정_예외처리() {
        // given
        LocalDate settingDate = LocalDate.now();
        Sector sector = Sector.createExceptRemovalDate("test", "test", settingDate,
            DEFAULT_GYM_ID, DEFAULT_SECTOR_IMAGE);

        // when & then
        assertThatThrownBy(() -> sector.updateRemovalDate(settingDate.minusDays(1)))
            .isInstanceOf(InvalidRemovalDateException.class);
    }
}
