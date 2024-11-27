package com.first.flash.climbing.sector.fixture;

import com.first.flash.climbing.sector.application.dto.SectorCreateRequestDto;
import com.first.flash.climbing.sector.domain.Sector;
import java.time.LocalDate;

public class SectorFixture {

    private static final String DEFAULT_SECTOR_IMAGE = "example image url";
    private final static Long DEFAULT_PLUS_DAYS = 30L;

    public static Sector createDefault(final Long gymId, final LocalDate settingDate) {
        return Sector.createDefault("sector 1", "admin sector 1",
            settingDate, settingDate.plusDays(DEFAULT_PLUS_DAYS), gymId, DEFAULT_SECTOR_IMAGE);
    }

    public static Sector createDefaultExceptRemovalDate(final Long gymId,
        final LocalDate settingDate) {
        return Sector.createExceptRemovalDate("sector 1", "admin sector 1",
            settingDate, gymId, DEFAULT_SECTOR_IMAGE);
    }

    public static SectorCreateRequestDto createDefaultRequestDtoExceptRemovalDate(
        final LocalDate settingDate) {
        return new SectorCreateRequestDto("sector 1", "admin sector 1",
            settingDate, null);
    }

    public static SectorCreateRequestDto createDefaultRequestDto(final LocalDate settingDate) {
        return new SectorCreateRequestDto("sector 1", "admin sector 1",
            settingDate, settingDate.plusDays(DEFAULT_PLUS_DAYS));
    }
}
