package com.LungCancerDetection.Security.Repository;

import com.LungCancerDetection.Security.Entity.DoctorEntity;
import com.LungCancerDetection.Security.Entity.HospitalEntity;
import com.LungCancerDetection.Security.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorEntity,Long> {
        List<DoctorEntity> findByHospital(HospitalEntity hospital);
        Optional<DoctorEntity> findByUser(UserEntity user);

    boolean existsByUser(UserEntity user);
}
