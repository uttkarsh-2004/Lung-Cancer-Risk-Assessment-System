package com.LungCancerDetection.Security.Dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;


@Data
public class SignUpRequestDto {
    private String username;

    private String email;
    private String password;
    private String phoneNumber;

}
