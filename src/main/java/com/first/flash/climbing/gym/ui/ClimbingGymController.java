package com.first.flash.climbing.gym.ui;

import com.first.flash.climbing.gym.application.ClimbingGymService;
import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateRequestDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateResponseDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymDetailResponseDto;
import com.first.flash.climbing.gym.infrastructure.dto.ClimbingGymResponseDto;
import com.first.flash.climbing.gym.domian.vo.Difficulty;
import com.first.flash.climbing.gym.exception.exceptions.DuplicateDifficultyLevelException;
import com.first.flash.climbing.gym.exception.exceptions.DuplicateDifficultyNameException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "gyms", description = "클라이밍장 조회 API")
@RestController
@RequiredArgsConstructor
public class ClimbingGymController {

    private final ClimbingGymService climbingGymService;

    @Operation(summary = "모든 클라이밍장 조회", description = "모든 클라이밍장을 리스트로 반환")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "클라이밍장 리스트 조회 성공함",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClimbingGymResponseDto.class)),
                examples = @ExampleObject(value = """
                        [
                            {
                                "id": 1,
                                "gymName": "더클라임 논현",
                                "thumbnailUrl": "example_map_image1.jpeg"
                            },
                            {
                                "id": 2,
                                "gymName": "더클라임 양재",
                                "thumbnailUrl": "example_map_image2.jpeg"
                            }
                        ]
                    """))),
    })
    @GetMapping("/gyms")
    public List<ClimbingGymResponseDto> getGyms() {
        return climbingGymService.findAllClimbingGyms().stream()
                                 .toList();
    }

    @Operation(summary = "클라이밍장 생성", description = "새로운 클라이밍장 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 클라이밍장을 생성함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClimbingGymCreateResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 형식",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "요청값 누락", value = "{\"gymName\": \"클라이밍장 이름은 필수입니다.\"}"),
                @ExampleObject(name = "난이도 이름 중복", value = "{\"error\": \"난이도 이름이 중복되었습니다: 파랑\"}"),
                @ExampleObject(name = "난이도 레벨 중복", value = "{\"error\": \"난이도 레벨이 중복되었습니다: 2\"}"),
            })),
    })
    @PostMapping("/admin/gyms")
    public ResponseEntity<ClimbingGymCreateResponseDto> createGym(
        @Valid @RequestBody final ClimbingGymCreateRequestDto gymCreateRequestDto) {

        validateDifficulties(gymCreateRequestDto.difficulties());

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(climbingGymService.save(gymCreateRequestDto));
    }

    private void validateDifficulties(final List<Difficulty> difficulties) {
        Set<String> names = new HashSet<>();
        Set<Integer> levels = new HashSet<>();

        for (Difficulty difficulty : difficulties) {
            if (!names.add(difficulty.getName())) {
                throw new DuplicateDifficultyNameException(difficulty.getName());
            }
            if (!levels.add(difficulty.getLevel())) {
                throw new DuplicateDifficultyLevelException(difficulty.getLevel());
            }
        }
    }

    @Operation(summary = "클라이밍장 정보 조회", description = "특정 클라이밍장의 정보 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 클라이밍장 정보를 조회함",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClimbingGymDetailResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "클라이밍장을 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "클라이밍장 없음", value = "{\"error\": \"아이디가 1인 클라이밍장을 찾을 수 없습니다.\"}")
            }))
    })
    @GetMapping("/gyms/{gymId}")
    public ResponseEntity<ClimbingGymDetailResponseDto> getGymDetails(
        @PathVariable final Long gymId) {
        return ResponseEntity.ok(climbingGymService.findClimbingGymDetail(gymId));
    }
}
