package com.LungCancerDetection.Security.Repository;

import com.LungCancerDetection.Security.Entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity,Long> {



}