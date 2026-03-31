package com.LungCancerDetection.Security.Repository;

import com.LungCancerDetection.Security.Entity.DoctorEntity;
import com.LungCancerDetection.Security.Entity.HospitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<DoctorEntity,Long> {
        List<DoctorEntity> findByHospital(HospitalEntity hospital);
}
