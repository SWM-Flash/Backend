package com.first.flash.account.auth.ui;

import com.first.flash.account.auth.application.AuthService;
import com.first.flash.account.auth.application.dto.LoginRequestDto;
import com.first.flash.account.auth.application.dto.LoginResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth", description = "인증/인가 API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "소셜 로그인", description = "소셜 로그인")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "소셜 로그인 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 형식",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "토큰값 누락", value = "{\"token\": \"토큰은 필수입니다.\"}"),
                @ExampleObject(name = "플랫폼값 누락", value = "{\"provider\": \"플랫폼은 필수입니다.\"}"),
                @ExampleObject(name = "플랫폼 유효성 검사 실패", value = "{\"provider\": \"유효하지 않은 값입니다.\"}"),
            }))
    })
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody final LoginRequestDto request) {
        LoginResponseDto loginResponse = authService.login(request);
        return ResponseEntity.ok(loginResponse);
    }
}
