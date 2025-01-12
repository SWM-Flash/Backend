package com.first.flash.climbing.gym.application;

import com.first.flash.climbing.favorite.application.MemberFavoriteGymService;
import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateRequestDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateResponseDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymDetailResponseDto;
import com.first.flash.climbing.gym.domian.ClimbingGymInfo;
import com.first.flash.climbing.gym.infrastructure.dto.ClimbingGymResponseDto;
import com.first.flash.climbing.gym.domian.ClimbingGym;
import com.first.flash.climbing.gym.domian.ClimbingGymRepository;
import com.first.flash.climbing.gym.exception.exceptions.ClimbingGymNotFoundException;
import com.first.flash.climbing.gym.infrastructure.dto.SectorInfoResponseDto;
import com.first.flash.global.util.AuthUtil;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClimbingGymService {

    private final ClimbingGymRepository climbingGymRepository;
    private final MemberFavoriteGymService memberFavoriteGymService;
    private final ClimbingGymInfoService climbingGymInfoService;

    @Transactional
    public ClimbingGymCreateResponseDto save(final Long gymInfoId,
        final ClimbingGymCreateRequestDto request) {
        ClimbingGym climbingGym = new ClimbingGym(request.gymName(), request.thumbnailUrl(),
            request.mapImageUrl(),
            request.calendarImageUrl(), gymInfoId);
        return ClimbingGymCreateResponseDto.toDto(climbingGymRepository.save(climbingGym));
    }

    public ClimbingGym findClimbingGymWithDifficultiesById(final Long id) {
        ClimbingGym climbingGym = climbingGymRepository.findById(id)
                                                       .orElseThrow(
                                                           () -> new ClimbingGymNotFoundException(
                                                               id));
        Long infoId = climbingGym.getGymInfoId();
        ClimbingGymInfo gymInfo = climbingGymInfoService.findById(infoId);
        climbingGym.updateDifficulties(gymInfo.getDifficulties());
        return climbingGym;
    }

    public List<ClimbingGymResponseDto> findAllClimbingGyms() {
        UUID memberId = AuthUtil.getId();
        List<Long> favoriteGymIds = memberFavoriteGymService.findFavoriteGymIdsByMemberId(memberId);
        return climbingGymRepository.findAllWithFavorites(favoriteGymIds);
    }

    public ClimbingGymDetailResponseDto findClimbingGymDetail(final Long id) {
        ClimbingGym climbingGym = findClimbingGymWithDifficultiesById(id);
        List<SectorInfoResponseDto> sectorNames = findSectorNamesById(id);
        List<String> difficultyNames = climbingGym.getDifficultyNames();
        return new ClimbingGymDetailResponseDto(climbingGym.getGymName(),
            climbingGym.getMapImageUrl(), climbingGym.getCalendarImageUrl(),
            difficultyNames, sectorNames);
    }

    private List<SectorInfoResponseDto> findSectorNamesById(final Long id) {
        return climbingGymRepository.findGymSectorNamesById(id);
    }
}
