package com.first.flash.climbing.solution.application;

import com.first.flash.account.member.application.MemberService;
import com.first.flash.account.member.domain.Member;
import com.first.flash.climbing.solution.application.dto.SolutionResponseDto;
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
public class SolutionCreateService {

    private final MemberService memberService;
    private final SolutionRepository solutionRepository;

    @Transactional
    public SolutionResponseDto saveSolution(final UUID problemId,
        final SolutionCreateRequestDto createRequestDto) {
        UUID id = AuthUtil.getId();
        Member member = memberService.findById(id);

        Solution solution = Solution.of(member.getNickName(), createRequestDto.review(),
            member.getInstagramId(),
            createRequestDto.videoUrl(), problemId, member.getId());
        Solution savedSolution = solutionRepository.save(solution);
        Events.raise(SolutionSavedEvent.of(savedSolution.getProblemId()));
        return SolutionResponseDto.toDto(savedSolution);
    }
}
