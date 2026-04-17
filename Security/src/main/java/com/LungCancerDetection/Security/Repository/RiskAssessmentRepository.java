package com.LungCancerDetection.Security.Repository;

import com.LungCancerDetection.Security.Entity.RiskAssessmentEntity;
import com.LungCancerDetection.Security.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiskAssessmentRepository extends JpaRepository<RiskAssessmentEntity,Long> {
    List<RiskAssessmentEntity> findByUser(UserEntity user);
    List<RiskAssessmentEntity> findByUserOrderByCreatedAtDesc(UserEntity user);

}
