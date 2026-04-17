package com.LungCancerDetection.Security.Dto;




import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientAppointmentDto {

    private Long appointmentId;
    private String doctorName;
    private String specialization;
    private String hospital;
    private String date;
    private String time;
    private String status;
}