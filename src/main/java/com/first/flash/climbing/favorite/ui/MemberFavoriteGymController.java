package com.first.flash.climbing.favorite.ui;

import com.first.flash.climbing.favorite.application.MemberFavoriteGymService;
import com.first.flash.climbing.favorite.application.dto.MemberFavoriteGymResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberFavoriteGymController {

    private final MemberFavoriteGymService memberFavoriteGymService;

    @Operation(summary = "클라이밍장 즐겨찾기 생성/삭제", description = "클라이밍장 id로 즐겨찾기 토글")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "즐겨찾기 생성 및 삭제",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberFavoriteGymResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 형식",
            content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<MemberFavoriteGymResponseDto> toggleMemberFavoriteGym(
        @PathVariable final Long gymId) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(memberFavoriteGymService.toggleMemberFavoriteGym(gymId));
    }
}
