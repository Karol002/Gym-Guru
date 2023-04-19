package com.gymguru.external.api.openai;

import com.gymguru.external.api.openai.dto.OpenAiBoardDto;
import com.gymguru.external.api.openai.dto.OpenAiDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OpenAiMapper {
    private final OpenAiConfiguration openAiConfiguration;

    public OpenAIRequest mapToOpenAiRequest(OpenAiMessage openAiMessage) {
        return new OpenAIRequest(
                openAiConfiguration.getOpenAiModel(),
                mapToOpenAiDetailsDtoList(openAiMessage),
                openAiConfiguration.getOpenAiTemperature(),
                openAiConfiguration.getMaxTokens()
        );
    }

    public List<OpenAiDetailsDto> mapToOpenAiDetailsDtoList(OpenAiMessage openAiMessage) {
        return Stream.of(openAiMessage)
                .map(content -> new OpenAiDetailsDto(openAiConfiguration.getRole(), openAiMessage.getContent()))
                .collect(Collectors.toList());
    }

    public OpenAiMessage mapToOpenAiMessageDto(OpenAiBoardDto objectDto) {
        return objectDto.getChoices().stream()
                .findFirst()
                .map(choice -> new OpenAiMessage(choice.getMessage().getContent()))
                .orElse(null);
    }
}