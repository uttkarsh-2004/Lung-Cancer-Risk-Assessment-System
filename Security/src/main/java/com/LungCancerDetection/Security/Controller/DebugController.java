package com.LungCancerDetection.Security.Controller;


import com.LungCancerDetection.Security.Entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/debug")
public class DebugController {

    // 🔹 Check full authentication object
    @GetMapping("/me")
    public Object me(Authentication authentication) {
        return authentication;
    }

    // 🔹 Check only username + roles (clean output)
    @GetMapping("/roles")
    public Object roles(Authentication authentication) {

        return Map.of(
                "username", authentication.getName(),
                "roles", authentication.getAuthorities()
        );
    }

    // 🔹 Check principal object (your UserEntity)
    @GetMapping("/user")
    public Object user(@AuthenticationPrincipal UserEntity user) {
        return user;
    }
}