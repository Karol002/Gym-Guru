package com.v1.gymguru.external.api.facade;

import com.v1.gymguru.external.api.openai.OpenAIRequest;
import com.v1.gymguru.external.api.openai.OpenAiClient;
import com.v1.gymguru.external.api.openai.OpenAiMapper;
import com.v1.gymguru.external.api.openai.OpenAiMessage;
import com.v1.gymguru.external.api.openai.dto.OpenAiObjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAiFacade {
    private final OpenAiClient openAiClient;
    private final OpenAiMapper openAiMapper;

    public OpenAiMessage getResponse(OpenAiMessage openAiMessage) {
        OpenAIRequest openAIRequest = openAiMapper.mapToOpenAiRequest(openAiMessage);
        OpenAiObjectDto objectDto = openAiClient.getOpenAiRequest(openAIRequest);
        return openAiMapper.mapToOpenAiMessageDto(objectDto);
    }
}
