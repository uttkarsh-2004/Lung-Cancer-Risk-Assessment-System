package com.LungCancerDetection.Security.Dto;


import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RescheduleRequestDto {
    private Long appointmentId;
    private LocalDate newDate;
    private LocalTime newTime;
}