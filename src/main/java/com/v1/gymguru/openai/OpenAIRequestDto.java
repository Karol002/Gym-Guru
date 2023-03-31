package com.v1.gymguru.openai;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpenAIRequestDto {
    private String model;
    private String prompt;
    private Double temperature;
    private Long max_tokens;

}
