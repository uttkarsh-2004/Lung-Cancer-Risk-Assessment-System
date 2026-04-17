package com.LungCancerDetection.Security.Dto;


import com.LungCancerDetection.Security.Enums.RiskLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RiskHistoryDto {

    private Double percentage;
    private RiskLevel riskLevel;
    private LocalDateTime assessedAt;
}