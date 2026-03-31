package com.LungCancerDetection.Security.Repository;

import com.LungCancerDetection.Security.Entity.RoleEntity;
import com.LungCancerDetection.Security.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByRole(RoleType role);
}
