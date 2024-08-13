package com.first.flash.account.member.ui;

import com.first.flash.account.member.application.BlockService;
import com.first.flash.account.member.application.MemberService;
import com.first.flash.account.member.application.dto.ConfirmNickNameRequest;
import com.first.flash.account.member.application.dto.ConfirmNickNameResponse;
import com.first.flash.account.member.application.dto.MemberBlockResponse;
import com.first.flash.account.member.application.dto.MemberCompleteRegistrationRequest;
import com.first.flash.account.member.application.dto.MemberCompleteRegistrationResponse;
import com.first.flash.account.member.application.dto.MemberInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "members", description = "회원 관리 API")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final BlockService blockService;

    @Operation(summary = "내 정보 조회", description = "특정 회원 정보 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberInfoResponse.class))),
    })
    @GetMapping
    public ResponseEntity<MemberInfoResponse> getMyInfo() {
        return ResponseEntity.ok(memberService.getMyInfo());
    }

    @Operation(summary = "회원 추가 정보 전체 수정", description = "특정 회원 추가 정보 전체 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 회원 추가 정보 수정",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberCompleteRegistrationResponse.class))),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 형식",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "요청값 누락", value = "{\"nickName\": \"닉네임은 필수입니다.\"}"),
                @ExampleObject(name = "닉네임 유효성 검사 실패", value = "{\"nickName\": \"닉네임은 1자에서 20자 사이의 한글, 영문, 숫자만 포함할 수 있습니다.\"}"),
                @ExampleObject(name = "인스타그램 아이디 유효성 검사 실패", value = "{\"instagramId\": \"인스타그램 아이디는 2자에서 30자 사이의 소문자 영문, 숫자, 밑줄, 온점(.)만 포함할 수 있습니다.\"}"),
                @ExampleObject(name = "키 유효성 검사 실패", value = "{\"height\": \"인스타그램 아이디는 2자에서 30자 사이의 소문자 영문, 숫자, 밑줄, 온점(.)만 포함할 수 있습니다.\"}"),
                @ExampleObject(name = "리치 유효성 검사 실패", value = "{\"reach\": \"인스타그램 아이디는 2자에서 30자 사이의 소문자 영문, 숫자, 밑줄, 온점(.)만 포함할 수 있습니다.\"}"),
                @ExampleObject(name = "성별 유효성 검사 실패", value = "{\"gender\": \"유효하지 않은 값입니다.\"}")
            })),
        @ApiResponse(responseCode = "409", description = "데이터 중복",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "닉네임 중복", value = "{\"error\": \"닉네임이 중복되었습니다!\"}")
            }))
    })
    @PatchMapping
    public ResponseEntity<MemberCompleteRegistrationResponse> completeMemberRegistration(
        @Valid @RequestBody final MemberCompleteRegistrationRequest request) {
        MemberCompleteRegistrationResponse response = memberService.completeMemberRegistration(
            request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 확인")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "중복 확인 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConfirmNickNameResponse.class))),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 형식",
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "요청값 누락", value = "{\"nickName\": \"닉네임은 필수입니다.\"}"),
                @ExampleObject(name = "닉네임 유효성 검사 실패", value = "{\"nickName\": \"닉네임은 1자에서 20자 사이의 한글, 영문, 숫자만 포함할 수 있습니다.\"}"),
            }))
    })
    @GetMapping("/nickname")
    public ResponseEntity<ConfirmNickNameResponse> confirmNickName(
        @Valid @RequestBody final ConfirmNickNameRequest request) {
        return ResponseEntity.ok(memberService.confirmNickName(request));
    }

    @PostMapping("/blocks/{blockedUserId}")
    public ResponseEntity<MemberBlockResponse> blockMember(@PathVariable final UUID blockedUserId) {
        return ResponseEntity.ok(blockService.blockMember(blockedUserId));
    }
}
