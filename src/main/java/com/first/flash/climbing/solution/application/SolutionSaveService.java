package com.first.flash.climbing.solution.application;

import com.first.flash.account.member.application.MemberService;
import com.first.flash.account.member.domain.Gender;
import com.first.flash.account.member.domain.Member;
import com.first.flash.climbing.solution.application.dto.SolutionWriteResponseDto;
import com.first.flash.climbing.solution.application.dto.UnregisteredMemberSolutionCreateRequest;
import com.first.flash.climbing.solution.domain.PerceivedDifficulty;
import com.first.flash.climbing.solution.domain.PerceivedDifficultySetEvent;
import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.SolutionRepository;
import com.first.flash.climbing.solution.domain.SolutionSavedEvent;
import com.first.flash.climbing.solution.domain.dto.SolutionCreateRequestDto;
import com.first.flash.global.event.Events;
import com.first.flash.global.util.AuthUtil;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SolutionSaveService {

    private final MemberService memberService;
    private final SolutionRepository solutionRepository;

    @Transactional
    public SolutionWriteResponseDto saveSolution(final UUID problemId,
        final SolutionCreateRequestDto createRequestDto) {
        UUID id = AuthUtil.getId();
        Member member = memberService.findById(id);

        PerceivedDifficulty perceivedDifficulty = createRequestDto.perceivedDifficulty();
        Solution solution = Solution.of(member.getNickName(), createRequestDto.review(),
            member.getInstagramId(), createRequestDto.thumbnailImageUrl(), createRequestDto.solvedDate(),
            createRequestDto.videoUrl(), problemId, member.getId(),
            member.getProfileImageUrl(), perceivedDifficulty, member.getHeight(), member.getReach(),
            member.getGender());
        Events.raise(PerceivedDifficultySetEvent.of(solution.getProblemId(),
            perceivedDifficulty.getValue()));

        Solution savedSolution = solutionRepository.save(solution);
        Events.raise(SolutionSavedEvent.of(savedSolution.getProblemId(), savedSolution.getId(),
            savedSolution.getSolutionDetail().getThumbnailImageUrl(),
            savedSolution.getUploaderDetail().getInstagramId()));

        return SolutionWriteResponseDto.toDto(savedSolution);
    }

    @Transactional
    public void updateUploaderInfo(final UUID uploaderId, final String nickName,
        final String instagramId, final String profileImageUrl, final Double uploaderHeight,
        final Double uploaderReach, final Gender uploaderGender) {
        solutionRepository.updateUploaderInfo(uploaderId, nickName, instagramId, profileImageUrl,
            uploaderHeight, uploaderReach, uploaderGender);
    }

    @Transactional
    public SolutionWriteResponseDto saveUnregisteredMemberSolution(final UUID problemId,
        final UnregisteredMemberSolutionCreateRequest requestDto) {
        UUID id = AuthUtil.getId();
        Member member = memberService.findById(id);

        PerceivedDifficulty perceivedDifficulty = requestDto.perceivedDifficulty();
        Solution solution = Solution.of(requestDto.nickName(), requestDto.review(),
            requestDto.instagramId(), requestDto.thumbnailImageUrl(), requestDto.solvedDate(),
            requestDto.videoUrl(), problemId, member.getId(),
            requestDto.profileImageUrl(), perceivedDifficulty, member.getHeight(),
            member.getReach(), member.getGender());

        Solution savedSolution = solutionRepository.save(solution);
        Events.raise(PerceivedDifficultySetEvent.of(solution.getProblemId(),
            perceivedDifficulty.getValue()));
        Events.raise(SolutionSavedEvent.of(savedSolution.getProblemId(), savedSolution.getId(),
            savedSolution.getSolutionDetail().getThumbnailImageUrl(),
            savedSolution.getUploaderDetail().getInstagramId()));
        return SolutionWriteResponseDto.toDto(savedSolution);
    }
}
