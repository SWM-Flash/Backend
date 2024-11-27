package com.first.flash.climbing.gym.domian;

import com.first.flash.climbing.gym.infrastructure.dto.SectorInfoResponseDto;
import java.util.List;
import java.util.Optional;

public interface ClimbingGymRepository {

    ClimbingGym save(final ClimbingGym gym);

    Optional<ClimbingGym> findById(final Long id);

    List<ClimbingGym> findAll();

    List<SectorInfoResponseDto> findGymSectorNamesById(final Long id);
}
