package com.LungCancerDetection.Security.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppointmentDashboardDto {

    private Long appointmentId;
    private String patientName;
    private String date;
    private String time;
    private String status;
    private Double amount;
}
