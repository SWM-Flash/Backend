package com.first.flash.climbing.gym.application;

import com.first.flash.climbing.gym.application.dto.ClimbingGymInfoCreateRequestDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymInfoResponseDto;
import com.first.flash.climbing.gym.domian.ClimbingGymInfo;
import com.first.flash.climbing.gym.domian.ClimbingGymInfoRepository;
import com.first.flash.climbing.gym.exception.exceptions.ClimbingGymInfoNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClimbingGymInfoService {

    private final ClimbingGymInfoRepository climbingGymInfoRepository;

    public ClimbingGymInfo findById(final Long id) {
        return climbingGymInfoRepository.findById(id)
                                        .orElseThrow(
                                            () -> new ClimbingGymInfoNotFoundException(id));
    }

    public ClimbingGymInfoResponseDto save(
        final ClimbingGymInfoCreateRequestDto gymInfoCreateRequestDto) {
        ClimbingGymInfo climbingGymInfo = gymInfoCreateRequestDto.toEntity();
        ClimbingGymInfo savedInfo = climbingGymInfoRepository.save(climbingGymInfo);
        return ClimbingGymInfoResponseDto.toDto(savedInfo);
    }

    public List<ClimbingGymInfoResponseDto> findAllClimbingGymInfos() {
        return climbingGymInfoRepository.findAll().stream()
            .map(ClimbingGymInfoResponseDto::toDto)
            .toList();
    }
}
