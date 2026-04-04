package com.LungCancerDetection.Security.Service;

import com.LungCancerDetection.Security.Dto.LoginRequestDto;
import com.LungCancerDetection.Security.Dto.LoginResponseDto;
import com.LungCancerDetection.Security.Dto.SignUpRequestDto;
import com.LungCancerDetection.Security.Dto.SignUpResponseDto;
import com.LungCancerDetection.Security.Entity.RoleEntity;
import com.LungCancerDetection.Security.Entity.UserEntity;
import com.LungCancerDetection.Security.Repository.RoleRepository;
import com.LungCancerDetection.Security.Repository.UserRepository;
import com.LungCancerDetection.Security.Enums.RoleType;
import com.LungCancerDetection.Security.Utility.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

    @Service
    @RequiredArgsConstructor
    public class AuthService {
        private final AuthenticationManager authenticationManager;
        private final AuthUtil authUtil;
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final RoleRepository roleRepository;

        public LoginResponseDto login(LoginRequestDto loginRequestDto){
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword()));

            UserEntity user = (UserEntity) authentication.getPrincipal();
            String token = authUtil.generateAccessToken(user);
            return  new LoginResponseDto(token, user.getUserId());
        }

        public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
            if(userRepository.existsByUserName(signUpRequestDto.getUsername())){
                throw new IllegalArgumentException("user already exists");
            }
            RoleEntity role = roleRepository.findByRole(RoleType.ROLE_PATIENT).orElseThrow(() -> new RuntimeException("Role not found"));
            UserEntity user = new UserEntity();
            user.setUserName(signUpRequestDto.getUsername());
            user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
            user.setRoles(Set.of(role));
            user = userRepository.save(user);
            return new SignUpResponseDto(user.getUserId(),user.getUsername());
        }

    }
