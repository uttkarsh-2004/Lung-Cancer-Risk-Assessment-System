package com.LungCancerDetection.Security.Dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateQuestionDto {

    private String questionText;
    private Integer maxScore;

    private List<CreateOptionDto> options;

}