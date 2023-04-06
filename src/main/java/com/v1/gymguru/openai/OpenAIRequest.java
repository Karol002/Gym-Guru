package com.v1.gymguru.openai;

import com.v1.gymguru.openai.dto.OpenAiDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OpenAIRequest {
    private String model;
    private List<OpenAiDetailsDto> messages;
    private Double temperature;
    private Long max_tokens;
}
