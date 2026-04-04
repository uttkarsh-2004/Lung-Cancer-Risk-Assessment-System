package com.LungCancerDetection.Security.Dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorResponseDto {
    private Long id;
    private String name;
    private String specialization;
    private String hospital;
    private Double consultationFee;
}