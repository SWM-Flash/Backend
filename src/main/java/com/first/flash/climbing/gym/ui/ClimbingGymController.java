package com.first.flash.climbing.gym.ui;

import com.first.flash.climbing.gym.application.ClimbingGymService;
import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateRequestDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateResponseDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymResponseDto;
import com.first.flash.climbing.gym.domian.ClimbingGym;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "gyms", description = "클라이밍장 조회 API")
@RestController
@RequestMapping("/gyms")
@RequiredArgsConstructor
public class ClimbingGymController {

    private final ClimbingGymService climbingGymService;

    @Operation(summary = "모든 클라이밍장 조회", description = "모든 클라이밍장을 리스트로 반환")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "클라이밍장 리스트 조회 성공함")
    })
    @GetMapping
    public List<ClimbingGymResponseDto> getGyms() {
        return climbingGymService.findAllClimbingGyms().stream()
            .toList();
    }

    @Operation(summary = "클라이밍장 생성", description = "새로운 클라이밍장 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 클라이밍장을 생성함"),
    })
    @PostMapping
    public ResponseEntity<ClimbingGymCreateResponseDto> createGym(
        @RequestBody final ClimbingGymCreateRequestDto gymCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(climbingGymService.save(gymCreateRequestDto));
    }

    @Operation(summary = "클라이밍장 정보 조회", description = "특정 클라이밍장의 정보 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 클라이밍장 정보를 조회함"),
        @ApiResponse(responseCode = "404", description = "클라이밍장을 찾을 수 없음")
    })
    @GetMapping("/{gymId}")
    public ClimbingGym getGymDetails(@PathVariable final Long gymId) {
        return climbingGymService.findClimbingGymById(gymId);
    }
}
