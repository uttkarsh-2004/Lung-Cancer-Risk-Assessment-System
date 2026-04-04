package com.LungCancerDetection.Security.Dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppointmentResponseDto {
    private Long appointmentId;
    private String status;
    private String paymentStatus;
    private Double amount;
}
