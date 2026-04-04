package com.LungCancerDetection.Security.Dto;

import com.LungCancerDetection.Security.Enums.QuestionCategory;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionResponseDto {

    private Long id;
    private String questionText;
    private Integer maxScore;
    private QuestionCategory category;

    private List<OptionResponseDto> options;

}