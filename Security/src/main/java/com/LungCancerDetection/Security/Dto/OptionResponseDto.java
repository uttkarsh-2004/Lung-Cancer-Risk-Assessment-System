package com.LungCancerDetection.Security.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionResponseDto {

    private Long id;
    private String optionText;
    private Integer score;

}