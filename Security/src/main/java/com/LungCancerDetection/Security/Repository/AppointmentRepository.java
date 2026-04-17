package com.LungCancerDetection.Security.Repository;

import com.LungCancerDetection.Security.Entity.AppointmentEntity;
import com.LungCancerDetection.Security.Entity.DoctorEntity;
import com.LungCancerDetection.Security.Entity.UserEntity;
import com.LungCancerDetection.Security.Enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    List<AppointmentEntity> findByPatient(UserEntity patient);

    List<AppointmentEntity> findByDoctor(DoctorEntity doctor);

    Optional<AppointmentEntity> findByDoctorAndDateAndTime(DoctorEntity doctor, LocalDate date, LocalTime time);

    List<AppointmentEntity> findByDoctorAndDate(DoctorEntity doctor, LocalDate date);

    List<AppointmentEntity> findByDoctorAndDateAfter(DoctorEntity doctor, LocalDate date);

    List<AppointmentEntity> findByDoctorAndDateBefore(DoctorEntity doctor, LocalDate date);
    Optional<AppointmentEntity> findByRazorpayOrderId(String orderId);
    Optional<AppointmentEntity> findByDoctorAndDateAndTimeAndStatusNot(
            DoctorEntity doctor,
            LocalDate date,
            LocalTime time,
            AppointmentStatus status
    );
}