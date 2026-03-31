package com.LungCancerDetection.Security.Repository;

import com.LungCancerDetection.Security.Entity.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<OptionEntity,Long> {
}
