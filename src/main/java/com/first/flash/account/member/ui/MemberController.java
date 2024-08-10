package com.first.flash.account.member.ui;

import com.first.flash.account.member.application.MemberService;
import com.first.flash.account.member.application.dto.ConfirmNickNameRequest;
import com.first.flash.account.member.application.dto.ConfirmNickNameResponse;
import com.first.flash.account.member.application.dto.MemberCompleteRegistrationRequest;
import com.first.flash.account.member.application.dto.MemberCompleteRegistrationResponse;
import com.first.flash.account.member.application.dto.MemberInfoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberInfoResponse> getMyInfo() {
        return ResponseEntity.ok(memberService.getMyInfo());
    }

    @PatchMapping
    public ResponseEntity<MemberCompleteRegistrationResponse> completeMemberRegistration(
        @Valid @RequestBody final MemberCompleteRegistrationRequest request) {
        MemberCompleteRegistrationResponse response = memberService.completeMemberRegistration(
            request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nickname")
    public ResponseEntity<ConfirmNickNameResponse> confirmNickName(
        @Valid @RequestBody final ConfirmNickNameRequest request) {
        return ResponseEntity.ok(memberService.confirmNickName(request));
    }
}
