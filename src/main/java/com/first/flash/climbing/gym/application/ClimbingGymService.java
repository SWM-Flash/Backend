package com.first.flash.climbing.gym.application;

import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateRequestDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymResponseDto;
import com.first.flash.climbing.gym.domian.ClimbingGym;
import com.first.flash.climbing.gym.domian.ClimbingGymRepository;
import com.first.flash.climbing.gym.exception.exceptions.ClimbingGymNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClimbingGymService {

    private final ClimbingGymRepository climbingGymRepository;

    @Transactional
    public ClimbingGymResponseDto save(final ClimbingGymCreateRequestDto createRequestDto) {
        ClimbingGym newGym = createRequestDto.toEntity();
        ClimbingGym savedGym = climbingGymRepository.save(newGym);
        return ClimbingGymResponseDto.toDto(savedGym);
    }

    public ClimbingGym findClimbingGymById(final Long id) {
        return climbingGymRepository.findById(id)
            .orElseThrow(()-> new ClimbingGymNotFoundException(id));
    }
}
