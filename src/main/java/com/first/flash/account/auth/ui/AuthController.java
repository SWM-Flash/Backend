package com.first.flash.account.auth.ui;

import com.first.flash.account.auth.application.AuthService;
import com.first.flash.account.auth.application.dto.LoginRequestDto;
import com.first.flash.account.auth.application.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody final LoginRequestDto request) {
        LoginResponseDto loginResponse = authService.login(request);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/admin/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("admin test");
    }
}
