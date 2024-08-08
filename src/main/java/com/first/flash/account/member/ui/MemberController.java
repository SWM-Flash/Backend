package com.first.flash.account.member.ui;

import com.first.flash.account.member.application.MemberService;
import com.first.flash.account.member.application.dto.ConfirmNickNameRequest;
import com.first.flash.account.member.application.dto.ConfirmNickNameResponse;
import com.first.flash.account.member.application.dto.MemberCompleteRegistrationRequest;
import com.first.flash.account.member.application.dto.MemberCompleteRegistrationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PatchMapping("/members")
    public ResponseEntity<MemberCompleteRegistrationResponse> completeMemberRegistration(
        @Valid @RequestBody final MemberCompleteRegistrationRequest request) {
        MemberCompleteRegistrationResponse response = memberService.completeMemberRegistration(
            request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/members/nickname")
    public ResponseEntity<ConfirmNickNameResponse> confirmNickName(
        @Valid @RequestBody final ConfirmNickNameRequest request) {
        return ResponseEntity.ok(memberService.confirmNickName(request));
    }
}
