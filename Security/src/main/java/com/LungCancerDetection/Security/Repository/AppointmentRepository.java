package com.LungCancerDetection.Security.Repository;

import com.LungCancerDetection.Security.Entity.AppointmentEntity;
import com.LungCancerDetection.Security.Entity.DoctorEntity;
import com.LungCancerDetection.Security.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    List<AppointmentEntity> findByPatient(UserEntity patient);

    List<AppointmentEntity> findByDoctor(DoctorEntity doctor);

    Optional<AppointmentEntity> findByDoctorAndDateAndTime(DoctorEntity doctor, LocalDate date, LocalTime time);
}