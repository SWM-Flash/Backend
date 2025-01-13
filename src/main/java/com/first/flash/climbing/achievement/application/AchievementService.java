package com.first.flash.climbing.achievement.application;

import com.first.flash.climbing.achievement.application.dto.AchievementCreateRequestDto;
import com.first.flash.climbing.achievement.application.dto.AchievementResponseDto;
import com.first.flash.climbing.achievement.application.dto.AchievementsResponseDto;
import com.first.flash.climbing.achievement.domain.Achievement;
import com.first.flash.climbing.achievement.domain.AchievementRepository;
import com.first.flash.climbing.achievement.exception.exceptions.AchievementAccessDeniedException;
import com.first.flash.climbing.achievement.exception.exceptions.AchievementLimitExceededException;
import com.first.flash.climbing.achievement.exception.exceptions.AchievementNotFoundException;
import com.first.flash.climbing.gym.application.ClimbingGymInfoService;
import com.first.flash.climbing.gym.domian.ClimbingGymInfo;
import com.first.flash.global.util.AuthUtil;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private static final int LIMIT_SIZE = 2;

    private final AchievementRepository achievementRepository;
    private final ClimbingGymInfoService gymInfoService;

    @Transactional
    public AchievementResponseDto save(final AchievementCreateRequestDto requestDto) {
        UUID memberId = AuthUtil.getId();
        List<Achievement> achievements = achievementRepository.findByMemberId(memberId);

        if (achievements.size() == LIMIT_SIZE) {
            throw new AchievementLimitExceededException();
        }

        ClimbingGymInfo gymInfo = gymInfoService.findById(requestDto.gymInfoId());
        long solutionCount = achievementRepository.findSolutionCountByGymInfoIdDifficulty(
            requestDto.gymInfoId(),
            requestDto.difficultyName(), memberId);

        Achievement newAchievement = Achievement.createDefault(solutionCount + 1,
            gymInfo.getGymInfoName(),
            requestDto.difficultyName(), gymInfo.getId(), memberId);
        achievementRepository.save(newAchievement);
        return AchievementResponseDto.toDto(newAchievement);
    }

    public AchievementsResponseDto findMyAchievements() {
        UUID memberId = AuthUtil.getId();
        List<Achievement> achievements = achievementRepository.findByMemberId(memberId);
        List<AchievementResponseDto> achievementsResponse = achievements.stream()
                                                                        .map(
                                                                            AchievementResponseDto::toDto)
                                                                        .toList();
        return AchievementsResponseDto.toDto(achievementsResponse);
    }

    @Transactional
    public void deleteAchievement(final Long id) {
        Achievement achievement = findById(id);
        if (!isValidMember(achievement.getMemberId())) {
            throw new AchievementAccessDeniedException();
        }
        achievementRepository.deleteById(id);
    }

    public Achievement findById(final Long id) {
        return achievementRepository.findById(id)
                                    .orElseThrow(() -> new AchievementNotFoundException(id));
    }

    private boolean isValidMember(final UUID memberId) {
        return AuthUtil.isSameId(memberId);
    }
}
