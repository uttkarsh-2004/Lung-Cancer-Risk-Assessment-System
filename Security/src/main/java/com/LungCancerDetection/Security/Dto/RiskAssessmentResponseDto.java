package com.LungCancerDetection.Security.Dto;

import com.LungCancerDetection.Security.Enums.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RiskAssessmentResponseDto {
    private Integer totalScore;
    private Integer maxScore;
    private Double percentage;
    private RiskLevel riskLevel;
    private String aiRecommendation;
    private LocalDateTime assessedAt;


}
