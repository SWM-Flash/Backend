package com.first.flash.climbing.sector.application;

import static com.first.flash.climbing.sector.fixture.SectorFixture.createDefault;
import static com.first.flash.climbing.sector.fixture.SectorFixture.createDefaultRequestDto;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.first.flash.climbing.sector.application.dto.SectorCreateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorUpdateRemovalDateRequestDto;
import com.first.flash.climbing.sector.domain.Sector;
import com.first.flash.climbing.sector.domain.SectorRepository;
import com.first.flash.climbing.sector.exception.exceptions.InvalidRemovalDateException;
import com.first.flash.climbing.sector.infrastructure.FakeSectorRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SectorServiceTest {

    private final static Long DEFAULT_GYM_ID = 1L;

    private SectorRepository sectorRepository;
    private SectorService sectorService;

    @BeforeEach
    void init() {
        sectorRepository = new FakeSectorRepository();
        sectorService = new SectorService(sectorRepository);
    }

    @Test
    void 섹터_저장() {
        // given
        SectorCreateRequestDto requestDto = createDefaultRequestDto(LocalDate.now());

        // when
        Long savedId = sectorService.saveSector(DEFAULT_GYM_ID, requestDto);
        Sector foundSector = sectorService.findById(savedId);

        // then
        assertThat(foundSector).isNotNull();
    }

    @Test
    void 섹터_탈거일_수정() {
        // given
        LocalDate settingDate = LocalDate.now();
        Long savedId = saveDefaultSector(settingDate);

        // when
        sectorService.updateSectorRemovalDate(savedId,
            new SectorUpdateRemovalDateRequestDto(settingDate.plusDays(1L)));

        // then
        Sector foundSector = sectorService.findById(savedId);
        assertThat(foundSector.getRemovalDate()).isEqualTo(settingDate.plusDays(1L));
    }

    @Test
    void 탈거일_수정_예외_처리() {
        // given
        LocalDate settingDate = LocalDate.now();
        Long savedId = saveDefaultSector(settingDate);

        // when & then
        assertThatThrownBy(() -> sectorService.updateSectorRemovalDate(savedId,
            new SectorUpdateRemovalDateRequestDto(settingDate.minusDays(1L))))
            .isInstanceOf(InvalidRemovalDateException.class);
    }

    private Long saveDefaultSector(final LocalDate settingDate) {
        Sector sector = createDefault(DEFAULT_GYM_ID, settingDate);
        return sectorRepository.save(sector);
    }
}
