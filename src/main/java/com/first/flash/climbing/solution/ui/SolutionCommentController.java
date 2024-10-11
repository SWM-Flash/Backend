package com.first.flash.climbing.solution.ui;

import com.first.flash.climbing.solution.application.SolutionCommentService;
import com.first.flash.climbing.solution.application.dto.SolutionCommentCreateRequestDto;
import com.first.flash.climbing.solution.application.dto.SolutionCommentCreateResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionCommentResponseDto;
import com.first.flash.climbing.solution.application.dto.SolutionCommentUpdateRequestDto;
import com.first.flash.climbing.solution.application.dto.SolutionCommentsResponseDto;
import com.first.flash.climbing.solution.infrastructure.dto.SolutionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "해설 댓글 생성", description = "특정 해설에 대한 댓글 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 댓글 생성",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SolutionCommentCreateResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 형식",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "요청값 누락", value = "{\"error\": \"content는 필수입니다.\"}"),
            })),
        @ApiResponse(responseCode = "404", description = "해설을 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "해설 없음", value = "{\"error\": \"아이디가 1인 해설을 찾을 수 없습니다.\"}")
            }))
    })
    @PostMapping("/solutions/{solutionId}/comments")
    public ResponseEntity<SolutionCommentCreateResponseDto> createSolutionComment(
        @PathVariable final Long solutionId,
        @RequestBody @Valid final SolutionCommentCreateRequestDto request) {
        SolutionCommentCreateResponseDto comment = solutionCommentService
            .createComment(solutionId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(comment);
    }

    @Operation(summary = "댓글 조회", description = "특정 해설에 대한 댓글 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 댓글을 조회함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SolutionCommentsResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "해설을 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "해설 없음", value = "{\"error\": \"아이디가 1인 해설을 찾을 수 없습니다.\"}")
            }))
    })
    @GetMapping("/solutions/{solutionId}/comments")
    public ResponseEntity<SolutionCommentsResponseDto> getSolutionComments(
        @PathVariable final Long solutionId) {
        SolutionCommentsResponseDto comments = solutionCommentService
            .findBySolutionId(solutionId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "댓글 수정", description = "내 댓글 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 댓글을 수정함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SolutionResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 형식",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "요청값 누락", value = "{\"error\": \"content는 필수입니다.\"}"),
            })),
        @ApiResponse(responseCode = "403", description = "본인의 댓글이 아님",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "수정 권한 없음", value = "{\"error\": \"해당 댓글에 접근할 권한이 없습니다.\"}"),
            })),
        @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "댓글 없음", value = "{\"error\": \"아이디가 1인 댓글을 찾을 수 없습니다.\"}")
            }))
    })
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<SolutionCommentResponseDto> updateComment(
        @PathVariable final Long commentId,
        @RequestBody @Valid final SolutionCommentUpdateRequestDto request) {
        SolutionCommentResponseDto response = solutionCommentService.updateComment(commentId,
            request);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(response);
    }

    @Operation(summary = "댓글 삭제", description = "특정 댓글 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "성공적으로 댓글을 삭제함",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "403", description = "본인의 댓글이 아님",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "삭제 권한 없음", value = "{\"error\": \"해당 댓글에 접근할 권한이 없습니다.\"}"),
            })),
        @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "댓글 없음", value = "{\"error\": \"아이디가 1인 댓글을 찾을 수 없습니다.\"}")
            }))
    })
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
        @PathVariable final Long commentId) {
        solutionCommentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
