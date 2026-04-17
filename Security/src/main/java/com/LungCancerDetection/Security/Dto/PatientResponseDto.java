package com.LungCancerDetection.Security.Dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientResponseDto {

    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
}