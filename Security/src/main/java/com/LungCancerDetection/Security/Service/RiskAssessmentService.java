package com.LungCancerDetection.Security.Service;

import com.LungCancerDetection.Security.Dto.AnswerDto;
import com.LungCancerDetection.Security.Dto.RiskAssessmentResponseDto;
import com.LungCancerDetection.Security.Entity.OptionEntity;
import com.LungCancerDetection.Security.Entity.RiskAssessmentEntity;
import com.LungCancerDetection.Security.Entity.UserEntity;
import com.LungCancerDetection.Security.Repository.OptionRepository;
import com.LungCancerDetection.Security.Repository.RiskAssessmentRepository;
import com.LungCancerDetection.Security.RiskLevel;
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

            insights.append("- ")
                    .append(option.getQuestion().getQuestionText())
                    .append(": ")
                    .append(option.getOptionText())
                    .append("\n");
        }

        String prompt = """
You are a smart and responsible medical assistant.

User Lung Cancer Risk Assessment:
- Risk Level: %s
- Risk Percentage: %.2f%%

User Key Insights:
%s

Your task is to give a highly personalized response.

FORMAT:

1. Risk Summary:
- Explain what this risk means specifically for THIS user.
- Avoid generic definitions.

2. Doctor Consultation:
- LOW → Usually No (unless symptoms present)
- MEDIUM → Suggest check-up
- HIGH → Strongly recommend doctor
- Justify based on user data

3. Recommended Tests:
- LOW → Avoid unnecessary tests
- MEDIUM → Basic screening
- HIGH → Immediate diagnostic tests

4. Personalized Lifestyle Advice:
- ONLY suggest what is relevant:
  - If smoker → quitting advice
  - If no smoking → DO NOT mention smoking
  - If symptoms present → address them
- Avoid generic advice

5. Warning Signs:
- Only relevant symptoms based on risk level
- Avoid repeating same points

IMPORTANT:
- Do NOT repeat content
- Do NOT give generic textbook answers
- Keep response under 150 words
- Make it feel like advice tailored to THIS user
- Use a supportive and calm tone

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
}