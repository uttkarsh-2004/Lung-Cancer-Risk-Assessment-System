package com.LungCancerDetection.Security.Controller;

import com.LungCancerDetection.Security.Dto.AnswerDto;
import com.LungCancerDetection.Security.Dto.RiskAssessmentResponseDto;
import com.LungCancerDetection.Security.Entity.UserEntity;
import com.LungCancerDetection.Security.Service.RiskAssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risk")
@RequiredArgsConstructor
public class RiskAssessmentController {

    private final RiskAssessmentService riskService;

    @PostMapping("/assess")
    public RiskAssessmentResponseDto assessRisk(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody List<AnswerDto> answers
    ) {

        return riskService.assess(user, answers);
    }
}