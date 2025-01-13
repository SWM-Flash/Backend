package com.first.flash.climbing.achievement.application;

import com.first.flash.climbing.achievement.application.dto.AchievementResponseDto;
import com.first.flash.climbing.achievement.application.dto.AchievementsResponseDto;
import com.first.flash.climbing.achievement.domain.Achievement;
import com.first.flash.climbing.achievement.domain.AchievementRepository;
import com.first.flash.climbing.achievement.exception.exceptions.AchievementNotFoundException;
import com.first.flash.global.util.AuthUtil;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementQueryService {

    private final AchievementRepository achievementRepository;

    public AchievementsResponseDto findMyAchievements() {
        UUID memberId = AuthUtil.getId();
        List<Achievement> achievements = achievementRepository.findByMemberId(memberId);
        List<AchievementResponseDto> achievementsResponse = achievements.stream()
                                                                        .map(
                                                                            AchievementResponseDto::toDto)
                                                                        .toList();
        return AchievementsResponseDto.toDto(achievementsResponse);
    }

    public Achievement findById(final Long id) {
        return achievementRepository.findById(id)
                                    .orElseThrow(() -> new AchievementNotFoundException(id));
    }
}
