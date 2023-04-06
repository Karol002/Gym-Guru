package com.v1.gymguru.openai.facade;

import com.v1.gymguru.openai.*;
import com.v1.gymguru.openai.dto.OpenAiObjectDto;
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
