package com.first.flash.climbing.gym.application;

import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateRequestDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateResponseDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymDetailResponseDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymResponseDto;
import com.first.flash.climbing.gym.domian.ClimbingGym;
import com.first.flash.climbing.gym.domian.ClimbingGymRepository;
import com.first.flash.climbing.gym.domian.vo.Difficulty;
import com.first.flash.climbing.gym.exception.exceptions.ClimbingGymNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClimbingGymService {

    private final ClimbingGymRepository climbingGymRepository;

    @Transactional
    public ClimbingGymCreateResponseDto save(final ClimbingGymCreateRequestDto createRequestDto) {
        ClimbingGym newGym = createRequestDto.toEntity();
        return ClimbingGymCreateResponseDto.toDto(climbingGymRepository.save(newGym));
    }

    public ClimbingGym findClimbingGymById(final Long id) {
        return climbingGymRepository.findById(id)
                                    .orElseThrow(() -> new ClimbingGymNotFoundException(id));
    }

    public List<ClimbingGymResponseDto> findAllClimbingGyms() {
        return climbingGymRepository.findAll().stream()
                                    .map(ClimbingGymResponseDto::toDto)
                                    .toList();
    }

    public ClimbingGymDetailResponseDto findClimbingGymDetail(final Long id) {
        ClimbingGym climbingGym = findClimbingGymById(id);
        List<String> sectorNames = findSectorNamesById(id);
        List<String> difficultyNames = getDifficultyNames(climbingGym);
        return new ClimbingGymDetailResponseDto(climbingGym.getGymName(),
            climbingGym.getMapImageUrl(), climbingGym.getCalendarImageUrl(),
            difficultyNames, sectorNames);
    }

    private List<String> findSectorNamesById(final Long id) {
        return climbingGymRepository.findGymSectorNamesById(id);
    }

    private List<String> getDifficultyNames(final ClimbingGym climbingGym) {
        return climbingGym.getDifficulties().stream()
                          .map(Difficulty::getName)
                          .toList();
    }
}
