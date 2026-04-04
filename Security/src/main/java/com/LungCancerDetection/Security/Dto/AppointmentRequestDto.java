package com.LungCancerDetection.Security.Dto;


import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentRequestDto {
    private Long doctorId;
    private LocalDate date;
    private LocalTime time;
}