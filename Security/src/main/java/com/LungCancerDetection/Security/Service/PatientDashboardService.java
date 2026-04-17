package com.LungCancerDetection.Security.Service;


import com.LungCancerDetection.Security.Dto.PatientResponseDto;
import com.LungCancerDetection.Security.Entity.AppointmentEntity;
import com.LungCancerDetection.Security.Entity.UserEntity;
import com.LungCancerDetection.Security.Repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientDashboardService {

    private final AppointmentRepository appointmentRepo;

    // 📅 Upcoming
    public List<AppointmentEntity> getUpcoming(UserEntity user) {
        return appointmentRepo.findByPatient(user)
                .stream()
                .filter(a -> a.getDate().isAfter(LocalDate.now()))
                .toList();
    }

    // 📜 History
    public List<AppointmentEntity> getHistory(UserEntity user) {
        return appointmentRepo.findByPatient(user)
                .stream()
                .filter(a -> a.getDate().isBefore(LocalDate.now()))
                .toList();
    }


        public PatientResponseDto getCurrentPatient(UserEntity user) {

            return PatientResponseDto.builder()
                    .id(user.getUserId())
                    .username(user.getUsername())
                    .build();
        }
}