package com.first.flash.climbing.hold.application;

import com.first.flash.climbing.hold.application.dto.HoldCreateRequestDto;
import com.first.flash.climbing.hold.application.dto.HoldResponseDto;
import com.first.flash.climbing.hold.application.dto.HoldsResponseDto;
import com.first.flash.climbing.hold.domain.Hold;
import com.first.flash.climbing.hold.domain.HoldRepository;
import com.first.flash.climbing.hold.exception.exceptions.HoldNotFoundException;
import com.first.flash.climbing.sector.application.dto.SectorCreateRequestDto;
import com.first.flash.climbing.sector.application.dto.SectorDetailResponseDto;
import com.first.flash.climbing.sector.application.dto.SectorsDetailResponseDto;
import com.first.flash.climbing.sector.domain.Sector;
import com.first.flash.climbing.sector.domain.SectorInfo;
import com.first.flash.climbing.sector.exception.exceptions.SectorNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoldService {

    private final HoldRepository holdRepository;

    public Hold findById(final Long id) {
        return holdRepository.findById(id)
                             .orElseThrow(() -> new HoldNotFoundException(id));
    }

    public HoldsResponseDto findAllHolds() {
        List<HoldResponseDto> holdsResponse = holdRepository.findAll()
                                         .stream()
                                         .map(HoldResponseDto::toDto)
                                         .toList();

        return new HoldsResponseDto(holdsResponse);
    }

    @Transactional
    public HoldResponseDto saveHold(final HoldCreateRequestDto createRequestDto) {
        Hold hold = createHold(createRequestDto);
        return HoldResponseDto.toDto(holdRepository.save(hold));
    }

    private Hold createHold(final HoldCreateRequestDto createRequestDto) {
        return Hold.of(createRequestDto.colorName(), createRequestDto.colorCode());
    }
}
