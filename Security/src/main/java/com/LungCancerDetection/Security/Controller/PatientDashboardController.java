package com.LungCancerDetection.Security.Controller;


import com.LungCancerDetection.Security.Dto.DoctorResponseDto;
import com.LungCancerDetection.Security.Dto.PatientAppointmentDto;
import com.LungCancerDetection.Security.Dto.PatientResponseDto;
import com.LungCancerDetection.Security.Dto.RiskHistoryDto;
import com.LungCancerDetection.Security.Entity.AppointmentEntity;
import com.LungCancerDetection.Security.Entity.UserEntity;
import com.LungCancerDetection.Security.Service.DoctorService;
import com.LungCancerDetection.Security.Service.PatientDashboardService;
import com.LungCancerDetection.Security.Service.RiskAssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientDashboardController {

    private final PatientDashboardService service;
    private final RiskAssessmentService riskAssessmentService;
    private final DoctorService doctorService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

    private PatientAppointmentDto map(AppointmentEntity a) {
        return PatientAppointmentDto.builder()
                .appointmentId(a.getId())
                .doctorName(a.getDoctor().getName())
                .specialization(a.getDoctor().getSpecialization())
                .hospital(a.getDoctor().getHospital().getName())
                .date(a.getDate().toString())
                .time(a.getTime().format(formatter))
                .status(a.getStatus().name())
                .build();
    }

    @GetMapping("/upcoming")
    public List<PatientAppointmentDto> upcoming(@AuthenticationPrincipal UserEntity user) {
        return service.getUpcoming(user).stream().map(this::map).toList();
    }

    @GetMapping("/history")
    public List<PatientAppointmentDto> history(@AuthenticationPrincipal UserEntity user) {
        return service.getHistory(user).stream().map(this::map).toList();
    }
    @GetMapping
    public List<DoctorResponseDto> getAllDoctors() {
        return doctorService.getAllDoctors();
    }
    @GetMapping("/me")
    public PatientResponseDto getMyProfile(
            @AuthenticationPrincipal UserEntity user
    ) {
        return service.getCurrentPatient(user);
    }
    @GetMapping("risk-history")
    public List<RiskHistoryDto> getHistory(@AuthenticationPrincipal UserEntity user) {
        return riskAssessmentService.getHistory(user);
    }
}