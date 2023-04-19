package com.gymguru.external.api.openai;

import com.gymguru.external.api.openai.dto.OpenAiBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OpenAiClient {
    private final RestTemplate restTemplate;
    private final OpenAiConfiguration openAiConfiguration;

    public OpenAiBoardDto getOpenAiRequest(OpenAIRequest requestBody) throws HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiConfiguration.getOpenAiKey());

        String url = openAiConfiguration.getOpenAiEndpoint();
        HttpEntity<OpenAIRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForObject(url, requestEntity, OpenAiBoardDto.class);
    }
}
