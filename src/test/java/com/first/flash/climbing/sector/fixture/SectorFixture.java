package com.first.flash.climbing.sector.fixture;

import com.first.flash.climbing.sector.application.dto.SectorCreateRequestDto;
import com.first.flash.climbing.sector.domain.Sector;
import java.time.LocalDate;

public class SectorFixture {

    private final static Long DEFAULT_PLUS_DAYS = 30L;

    public static Sector createDefault(final Long gymId) {
        return Sector.createDefault("sector 1", "admin sector 1",
            LocalDate.now(), LocalDate.now().plusDays(DEFAULT_PLUS_DAYS), gymId);
    }

    public static Sector createDefaultExceptRemovalDate(final Long gymId) {
        return Sector.createExceptRemovalDate("sector 1", "admin sector 1",
            LocalDate.now(), gymId);
    }

    public static SectorCreateRequestDto createDefaultRequestDtoExceptRemovalDate(
        final Long gymId) {
        return new SectorCreateRequestDto("sector 1", "admin sector 1",
            LocalDate.now(), null, gymId);
    }

    public static SectorCreateRequestDto createDefaultRequestDto(final Long gymId) {
        return new SectorCreateRequestDto("sector 1", "admin sector 1",
            LocalDate.now(), LocalDate.now().plusDays(DEFAULT_PLUS_DAYS), gymId);
    }
}
