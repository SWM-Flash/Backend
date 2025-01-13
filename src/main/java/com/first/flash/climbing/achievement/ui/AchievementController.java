package com.first.flash.climbing.achievement.ui;

import com.first.flash.climbing.achievement.application.AchievementService;
import com.first.flash.climbing.achievement.application.dto.AchievementCreateRequestDto;
import com.first.flash.climbing.achievement.application.dto.AchievementResponseDto;
import com.first.flash.climbing.achievement.application.dto.AchievementsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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

    private final AchievementService achievementService;

    @Operation(summary = "내 업적 조회", description = "본인이 생성한 업적 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 업적 조회함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AchievementsResponseDto.class)))
    })
    @GetMapping("/achievements")
    public ResponseEntity<AchievementsResponseDto> getMySolutions() {
        return ResponseEntity.ok(achievementService.findMyAchievements());
    }

    @Operation(summary = "업적 생성", description = "업적 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 업적 생성함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AchievementResponseDto.class)))
    })
    @PostMapping("/achievements")
    public ResponseEntity<AchievementResponseDto> saveAchievement(
        @Valid @RequestBody AchievementCreateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(achievementService.save(requestDto));
    }

    @Operation(summary = "업적 생성", description = "업적 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 업적 생성함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AchievementResponseDto.class)))
    })
    @DeleteMapping("/achievements/{id}")
    public ResponseEntity<Void> deleteAchievement(
        @PathVariable Long id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.noContent().build();
    }
}
