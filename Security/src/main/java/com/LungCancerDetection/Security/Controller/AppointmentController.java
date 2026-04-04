package com.LungCancerDetection.Security.Controller;


import com.LungCancerDetection.Security.Dto.AppointmentRequestDto;
import com.LungCancerDetection.Security.Dto.AppointmentResponseDto;
import com.LungCancerDetection.Security.Entity.AppointmentEntity;
import com.LungCancerDetection.Security.Entity.UserEntity;
import com.LungCancerDetection.Security.Service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // 🔥 Step 1: Initiate booking
    @PostMapping("/initiate")
    public AppointmentResponseDto initiateAppointment(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody AppointmentRequestDto request
    ) {

        AppointmentEntity appointment = appointmentService.initiateAppointment(
                user,
                request.getDoctorId(),
                request.getDate(),
                request.getTime()
        );

        return AppointmentResponseDto.builder()
                .appointmentId(appointment.getId())
                .status(appointment.getStatus().name())
                .paymentStatus(appointment.getPaymentStatus().name())
                .amount(appointment.getAmount())
                .build();
    }
}
