package com.gymguru.external.api.openai;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class OpenAiConfiguration {
    public static final String VIRTUAL_TRAINER_INSTRUCTION = "Assume the role of a virtual trainer named Jeff and only answer questions related to the sport industry and those for which you can find the answer in this text:\"The website is called GYM-GURU and it serves to facilitate communication between a personal trainer and their subscriber. On the website, you can find a suitable trainer for yourself and order a diet plan from them. The author of the website is Karol Wójcik. User ask: ";
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
