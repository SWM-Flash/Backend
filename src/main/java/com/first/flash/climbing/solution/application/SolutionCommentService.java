package com.first.flash.climbing.solution.application;

import com.first.flash.account.member.application.MemberService;
import com.first.flash.account.member.domain.Member;
import com.first.flash.climbing.solution.application.dto.SolutionCommentCreateRequestDto;
import com.first.flash.climbing.solution.application.dto.SolutionCommentCreateResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionCommentResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionCommentsResponseDto;
import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.SolutionComment;
import com.first.flash.climbing.solution.infrastructure.SolutionCommentRepository;
import com.first.flash.global.util.AuthUtil;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SolutionCommentService {

    private final MemberService memberService;
    private final SolutionCommentRepository solutionCommentRepository;
    private final SolutionService solutionService;

    public SolutionCommentCreateResponseDto createComment(final Long solutionId,
        final SolutionCommentCreateRequestDto requestDto) {
        UUID id = AuthUtil.getId();
        Member member = memberService.findById(id);

        Solution solution = solutionService.findSolutionById(solutionId);
        SolutionComment solutionComment = SolutionComment.of(requestDto.content(),
            member.getNickName(), member.getId(), solution);
        SolutionComment savedSolutionComment = solutionCommentRepository.save(solutionComment);

        return SolutionCommentCreateResponseDto.toDto(savedSolutionComment);
    }

    public SolutionCommentsResponseDto findBySolutionId(final Long solutionId) {
        List<SolutionComment> comments = solutionCommentRepository.findBySolutionId(solutionId);
        List<SolutionCommentResponseDto> commentsResponse = comments.stream()
                                                                    .map(
                                                                        SolutionCommentResponseDto::toDto)
                                                                    .toList();
        return SolutionCommentsResponseDto.from(commentsResponse);
    }
}
