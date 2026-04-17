package com.LungCancerDetection.Security.Service;

import com.LungCancerDetection.Security.Dto.AnswerDto;
import com.LungCancerDetection.Security.Dto.RiskAssessmentResponseDto;
import com.LungCancerDetection.Security.Dto.RiskHistoryDto;
import com.LungCancerDetection.Security.Entity.OptionEntity;
import com.LungCancerDetection.Security.Entity.RiskAssessmentEntity;
import com.LungCancerDetection.Security.Entity.UserEntity;
import com.LungCancerDetection.Security.Repository.OptionRepository;
import com.LungCancerDetection.Security.Repository.RiskAssessmentRepository;
import com.LungCancerDetection.Security.Enums.RiskLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiskAssessmentService {

    private final OptionRepository optionRepository;
    private final RiskAssessmentRepository riskRepo;
    private final AIService aiService;

//    public RiskAssessmentResponseDto assess(UserEntity user, List<AnswerDto> answers) {
//
//        int totalScore = 0;
//        int maxScore = 0;
//
//        for (AnswerDto ans : answers) {
//
//            OptionEntity option = optionRepository.findById(ans.getOptionId())
//                    .orElseThrow(() -> new RuntimeException("Invalid option"));
//
//            totalScore += option.getScore();
//            maxScore += option.getQuestion().getMaxScore();
//        }
//
//        double percentage = (totalScore * 100.0) / maxScore;
//
//        RiskLevel level =
//                percentage >= 70 ? RiskLevel.HIGH :
//                        percentage >= 40 ? RiskLevel.MEDIUM :
//                                RiskLevel.LOW;
//
//        RiskAssessmentEntity assessment = new RiskAssessmentEntity();
//        assessment.setUser(user);
//        assessment.setTotalScore(totalScore);
//        assessment.setMaxScore(maxScore);
//        assessment.setPercentage(percentage);
//        assessment.setRiskLevel(level);
//
//        assessment = riskRepo.save(assessment);
//
//        return RiskAssessmentResponseDto.builder()
//                .totalScore(totalScore)
//                .maxScore(maxScore)
//                .percentage(percentage)
//                .riskLevel(level)
//                .assessedAt(assessment.getCreatedAt())
//                .build();
//    }

    public RiskAssessmentResponseDto assess(UserEntity user, List<AnswerDto> answers) {

        int totalScore = 0;
        int maxScore = 0;

        for (AnswerDto ans : answers) {

            OptionEntity option = optionRepository.findById(ans.getOptionId())
                    .orElseThrow(() -> new RuntimeException("Invalid option"));

            totalScore += option.getScore();
            maxScore += option.getQuestion().getMaxScore();
        }

        double percentage = (totalScore * 100.0) / maxScore;

        RiskLevel level =
                percentage >= 70 ? RiskLevel.HIGH :
                        percentage >= 40 ? RiskLevel.MEDIUM :
                                RiskLevel.LOW;

        RiskAssessmentEntity assessment = new RiskAssessmentEntity();
        assessment.setUser(user);
        assessment.setTotalScore(totalScore);
        assessment.setMaxScore(maxScore);
        assessment.setPercentage(percentage);
        assessment.setRiskLevel(level);
        assessment = riskRepo.save(assessment);

        // 🧠 Build user insights
        StringBuilder insights = new StringBuilder();

        for (AnswerDto ans : answers) {
            OptionEntity option = optionRepository.findById(ans.getOptionId()).orElseThrow();

            String category = option.getQuestion().getCategory().name();

            insights.append("- Category: ").append(category).append("\n")
                    .append("  Question: ").append(option.getQuestion().getQuestionText()).append("\n")
                    .append("  Answer: ").append(option.getOptionText()).append("\n")
                    .append("  Score Impact: ").append(option.getScore()).append("\n\n");
        }

        String prompt = """
You are a medical assistant analyzing lung cancer risk.

Risk:
- Level: %s
- Percentage: %.2f%%

Patient Data:
%s

Give a SHORT personalized response:

1. Why this risk? (based on user data)
2. Should user see a doctor?
3. Suggested tests (if needed)
4. Key advice (only relevant)
5. Warning signs

Rules:
- Max 100 words
- No generic lines
- Focus only on given data
- Be clear and direct
""".formatted(level, percentage, insights.toString());
        String aiResponse;
        try {
            aiResponse = aiService.getRecommendation(prompt);
        } catch (Exception e) {
            aiResponse = "AI service unavailable. Please consult a doctor.";
        }

// ✅ Return response
        return RiskAssessmentResponseDto.builder()
                .totalScore(totalScore)
                .maxScore(maxScore)
                .percentage(percentage)
                .riskLevel(level)
                .assessedAt(assessment.getCreatedAt())
                .aiRecommendation(aiResponse) // NEW FIELD
                .build();
    }
    public List<RiskHistoryDto> getHistory(UserEntity user) {

        return riskRepo.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(r -> RiskHistoryDto.builder()
                        .percentage(r.getPercentage())
                        .assessedAt(r.getCreatedAt())
                        .riskLevel(r.getRiskLevel())
                        .build()
                ).toList();
    }
}