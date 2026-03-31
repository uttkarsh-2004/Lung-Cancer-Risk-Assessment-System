package com.LungCancerDetection.Security.Dto;

import lombok.Data;

import java.util.List;

@Data
public class RiskSubmitRequestDto {
    private List<AnswerDto> answers;

}
