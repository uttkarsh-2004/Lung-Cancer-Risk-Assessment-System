package com.LungCancerDetection.Security.Controller;



import com.LungCancerDetection.Security.Dto.AppointmentDashboardDto;
import com.LungCancerDetection.Security.Dto.RescheduleRequestDto;
import com.LungCancerDetection.Security.Dto.UpdateFeeDto;
import com.LungCancerDetection.Security.Entity.AppointmentEntity;
import com.LungCancerDetection.Security.Entity.UserEntity;
import com.LungCancerDetection.Security.Service.DoctorDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/doctor/dashboard")
@RequiredArgsConstructor
public class DoctorDashboardController {

    private final DoctorDashboardService service;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

    private AppointmentDashboardDto map(AppointmentEntity a) {
        return AppointmentDashboardDto.builder()
                .appointmentId(a.getId())
                .patientName(a.getPatient().getUsername())
                .date(a.getDate().toString())
                .time(a.getTime().format(formatter))
                .status(a.getStatus().name())
                .amount(a.getAmount())
                .build();
    }

    @GetMapping("/today")
    public List<AppointmentDashboardDto> today(@AuthenticationPrincipal UserEntity user) {
        return service.getTodayAppointments(user).stream().map(this::map).toList();
    }

    @GetMapping("/upcoming")
    public List<AppointmentDashboardDto> upcoming(@AuthenticationPrincipal UserEntity user) {
        return service.getUpcoming(user).stream().map(this::map).toList();
    }

    @GetMapping("/history")
    public List<AppointmentDashboardDto> history(@AuthenticationPrincipal UserEntity user) {
        return service.getHistory(user).stream().map(this::map).toList();
    }

    @PostMapping("/cancel/{id}")
    public String cancel(@AuthenticationPrincipal UserEntity user, @PathVariable Long id) {
        service.cancelAppointment(user, id);
        return "Cancelled";
    }

    @PostMapping("/reschedule")
    public String reschedule(@AuthenticationPrincipal UserEntity user,
                             @RequestBody RescheduleRequestDto req) {
        service.reschedule(user, req.getAppointmentId(), req.getNewDate(), req.getNewTime());
        return "Rescheduled";
    }

    @PostMapping("/update-fee")
    public String updateFee(@AuthenticationPrincipal UserEntity user,
                            @RequestBody UpdateFeeDto dto) {
        service.updateFee(user, dto.getConsultationFee());
        return "Fee updated";
    }
}
