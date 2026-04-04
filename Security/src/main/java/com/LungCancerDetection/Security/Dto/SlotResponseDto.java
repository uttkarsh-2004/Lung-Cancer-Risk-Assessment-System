package com.LungCancerDetection.Security.Dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SlotResponseDto {
    private List<String> slots;
    private List<String> bookedSlots;
}