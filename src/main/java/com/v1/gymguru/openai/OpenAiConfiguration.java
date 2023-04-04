package com.v1.gymguru.openai;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class OpenAiConfiguration {
    @Value("${openai.api.endpoint.prod}")
    private String openAiEndpoint;
    @Value("${openai.api.key}")
    private String openAiKey;
    @Value("${openai.api.model}")
    private String openAiModel;
    @Value("${openai.api.temperature}")
    private double openAiTemperature;
    @Value("${openai.api.max.tokens}")
    private long maxTokens;
    @Value("${openai.api.role}")
    private String role;
}
