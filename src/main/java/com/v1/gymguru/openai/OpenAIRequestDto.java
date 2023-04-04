package com.v1.gymguru.openai;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OpenAIRequestDto {
    private String model;
    private List<OpenAiDetailsDto> messages;
    private Double temperature;
    private Long max_tokens;
}
