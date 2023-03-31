package com.v1.gymguru.openai;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OpenAiClient {
    private final RestTemplate restTemplate;
    private final OpenAiConfiguration openAiConfiguration;

    public OpenAiResponseDto getOpenAiRequest(OpenAiResponseDto ask) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(openAiConfiguration.getOpenAiKey());

        String url = openAiConfiguration.getOpenAiEndpoint();

        OpenAIRequestDto requestBody = new OpenAIRequestDto(openAiConfiguration.getOpenAiModel(), ask.getResponse(), 0.5, 3000L);

        HttpEntity<OpenAIRequestDto> requestEntity = new HttpEntity<>(requestBody, headers);
        OpenAiObjectDto response = restTemplate.postForObject(url, requestEntity, OpenAiObjectDto.class);

        assert response != null;
        return response.getResponses().get(0);
    }
}
