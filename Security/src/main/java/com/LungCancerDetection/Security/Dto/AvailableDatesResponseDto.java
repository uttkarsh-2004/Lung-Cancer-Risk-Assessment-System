package com.LungCancerDetection.Security.Dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AvailableDatesResponseDto {
    private List<String> availableDates;
}