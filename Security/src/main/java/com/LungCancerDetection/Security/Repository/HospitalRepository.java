package com.LungCancerDetection.Security.Repository;

import com.LungCancerDetection.Security.Entity.HospitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<HospitalEntity,Long> {
    Optional<HospitalEntity> findByName(String hospitalName);
}
