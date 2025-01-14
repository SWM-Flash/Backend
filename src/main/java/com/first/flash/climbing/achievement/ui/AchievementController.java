package com.first.flash.climbing.achievement.ui;

import com.first.flash.climbing.achievement.application.AchievementCommandService;
import com.first.flash.climbing.achievement.application.AchievementQueryService;
import com.first.flash.climbing.achievement.application.dto.AchievementCreateRequestDto;
import com.first.flash.climbing.achievement.application.dto.AchievementResponseDto;
import com.first.flash.climbing.achievement.application.dto.AchievementsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementQueryService queryService;
    private final AchievementCommandService commandService;

    @Operation(summary = "내 업적 조회", description = "본인이 생성한 업적 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 업적 조회함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AchievementsResponseDto.class)))
    })
    @GetMapping("/achievements")
    public ResponseEntity<AchievementsResponseDto> getMySolutions() {
        return ResponseEntity.ok(queryService.findMyAchievements());
    }

    @Operation(summary = "업적 생성", description = "업적 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 업적 생성함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AchievementResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "업적 최대 개수 초과",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "업적 최대 개수 초과", value = "{\"error\": \"최대 업적 개수는 2개입니다.\"}"),
            }))
    })
    @PostMapping("/achievements")
    public ResponseEntity<AchievementResponseDto> saveAchievement(
        @Valid @RequestBody AchievementCreateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(commandService.save(requestDto));
    }

    @Operation(summary = "업적 삭제", description = "업적 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "성공적으로 업적 생성함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AchievementResponseDto.class))),
        @ApiResponse(responseCode = "403", description = "본인의 업적이 아님",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "삭제 권한 없음", value = "{\"error\": \"해당 업적에 접근할 권한이 없습니다.\"}"),
            })),
        @ApiResponse(responseCode = "404", description = "업적을 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "업적을 찾을 수 없음", value = "{\"error\": \"아이디가 1인 업적을 찾을 수 없습니다.\"}"),
            }))
    })
    @DeleteMapping("/achievements/{id}")
    public ResponseEntity<Void> deleteAchievement(
        @PathVariable Long id) {
        commandService.deleteAchievement(id);
        return ResponseEntity.noContent().build();
    }
}
