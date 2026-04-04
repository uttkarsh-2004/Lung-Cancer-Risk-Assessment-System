package com.LungCancerDetection.Security.Repository;

import com.LungCancerDetection.Security.Entity.DoctorEntity;
import com.LungCancerDetection.Security.Entity.TimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, Long> {

    List<TimeSlotEntity> findByDoctorAndDate(DoctorEntity doctor, LocalDate date);
}