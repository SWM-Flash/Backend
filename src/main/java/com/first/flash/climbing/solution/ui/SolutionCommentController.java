package com.first.flash.climbing.solution.ui;

import com.first.flash.climbing.solution.application.SolutionCommentService;
import com.first.flash.climbing.solution.application.dto.SolutionCommentCreateRequestDto;
import com.first.flash.climbing.solution.application.dto.SolutionCommentCreateResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionCommentUpdateRequestDto;
import com.first.flash.climbing.solution.application.dto.SolutionCommentsResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "solutions", description = "해설 관리 API")
@RestController
@RequiredArgsConstructor
public class SolutionCommentController {

    private final SolutionCommentService solutionCommentService;

    @PostMapping("/solutions/{solutionId}/comments")
    public ResponseEntity<SolutionCommentCreateResponseDto> createSolutionComment(
        @PathVariable final Long solutionId,
        @RequestBody @Valid final SolutionCommentCreateRequestDto request) {
        SolutionCommentCreateResponseDto comment = solutionCommentService
            .createComment(solutionId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(comment);
    }

    @GetMapping("/solutions/{solutionId}/comments")
    public ResponseEntity<SolutionCommentsResponseDto> getSolutionComments(
        @PathVariable final Long solutionId) {
        SolutionCommentsResponseDto comments = solutionCommentService
            .findBySolutionId(solutionId);
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<Void> updateComment(
        @PathVariable final Long commentId,
        @RequestBody @Valid final SolutionCommentUpdateRequestDto content) {
        solutionCommentService.updateComment(commentId, content);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
        @PathVariable final Long commentId) {
        solutionCommentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
