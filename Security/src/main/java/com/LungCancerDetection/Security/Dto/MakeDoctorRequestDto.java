package com.LungCancerDetection.Security.Dto;

import lombok.Data;

@Data
public class MakeDoctorRequestDto {
    private Long id;
    private String doctorName;
    private String specialization;
    private String hospitalName;
    private String city;
    private String contactNumber;
    private Double consultationFee;
    private String urlImage;
}
