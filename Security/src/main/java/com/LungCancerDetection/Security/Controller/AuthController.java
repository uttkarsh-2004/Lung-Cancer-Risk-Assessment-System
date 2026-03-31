package com.LungCancerDetection.Security.Controller;

import com.LungCancerDetection.Security.Dto.LoginRequestDto;
import com.LungCancerDetection.Security.Dto.LoginResponseDto;
import com.LungCancerDetection.Security.Dto.SignUpRequestDto;
import com.LungCancerDetection.Security.Dto.SignUpResponseDto;
import com.LungCancerDetection.Security.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.ok(authService.signUp(signUpRequestDto));
    }
}
