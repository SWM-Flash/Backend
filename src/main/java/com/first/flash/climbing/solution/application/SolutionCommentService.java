package com.first.flash.climbing.solution.application;

import com.first.flash.account.member.application.MemberService;
import com.first.flash.account.member.domain.Member;
import com.first.flash.climbing.solution.application.dto.SolutionCommentCreateRequestDto;
import com.first.flash.climbing.solution.application.dto.SolutionCommentCreateResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionCommentResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionCommentUpdateRequestDto;
import com.first.flash.climbing.solution.application.dto.SolutionCommentsResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionResponseDto;
import com.first.flash.climbing.solution.domain.Solution;
import com.first.flash.climbing.solution.domain.SolutionComment;
import com.first.flash.climbing.solution.exception.exceptions.SolutionCommentAccessDeniedException;
import com.first.flash.climbing.solution.exception.exceptions.SolutionCommentNotFoundException;
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

    @Transactional
    public SolutionCommentCreateResponseDto createComment(final Long solutionId,
        final SolutionCommentCreateRequestDto requestDto) {
        UUID id = AuthUtil.getId();
        Member member = memberService.findById(id);

        Solution solution = solutionService.findSolutionById(solutionId);
        SolutionComment solutionComment = SolutionComment.of(requestDto.content(),
            member.getNickName(), member.getProfileImageUrl(), member.getId(), solution);
        SolutionComment savedSolutionComment = solutionCommentRepository.save(solutionComment);

        return SolutionCommentCreateResponseDto.toDto(savedSolutionComment);
    }

    public SolutionComment findById(final Long id) {
        return solutionCommentRepository.findById(id)
                                        .orElseThrow(
                                            () -> new SolutionCommentNotFoundException(id));
    }

    public SolutionCommentsResponseDto findBySolutionId(final Long solutionId) {
        List<SolutionComment> comments = solutionCommentRepository.findBySolutionId(solutionId);
        List<SolutionCommentResponseDto> commentsResponse = comments.stream()
                                                                    .map(
                                                                        SolutionCommentResponseDto::toDto)
                                                                    .toList();
        return SolutionCommentsResponseDto.from(commentsResponse);
    }

    @Transactional
    public SolutionResponseDto updateComment(final Long commentId, final SolutionCommentUpdateRequestDto request) {
        SolutionComment comment = findById(commentId);
        if (!AuthUtil.isSameId(comment.getCommenterDetail().getCommenterId())) {
            throw new SolutionCommentAccessDeniedException();
        }
        comment.updateContent(request.content());
        return SolutionResponseDto.toDto(comment.getSolution());
    }

    @Transactional
    public void deleteComment(final Long commentId) {
        SolutionComment comment = findById(commentId);
        if (!AuthUtil.isSameId(comment.getCommenterDetail().getCommenterId())) {
            throw new SolutionCommentAccessDeniedException();
        }
        solutionCommentRepository.delete(comment);
    }

    @Transactional
    public void deleteByCommenterId(final UUID commenterId) {
        solutionCommentRepository.deleteByCommenterId(commenterId);
    }

    @Transactional
    public void updateCommenterInfo(final UUID commenterId, final String nickName,
        final String profileImageUrl) {
        solutionCommentRepository.updateCommenterInfo(commenterId, nickName, profileImageUrl);
    }
}
