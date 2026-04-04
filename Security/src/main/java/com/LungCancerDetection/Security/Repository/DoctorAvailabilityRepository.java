package com.LungCancerDetection.Security.Repository;

import com.LungCancerDetection.Security.Entity.DoctorAvailabilityEntity;
import com.LungCancerDetection.Security.Entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailabilityEntity, Long> {

    List<DoctorAvailabilityEntity> findByDoctorAndIsAvailableTrue(DoctorEntity doctor);

    boolean existsByDoctorAndDate(DoctorEntity doctor, LocalDate date);
}