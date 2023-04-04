package com.v1.gymguru.openai;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiClient {
    private final RestTemplate restTemplate;
    private final OpenAiConfiguration openAiConfiguration;

/*    public Object getOpenAiRequest(OpenAiMessageDto ask) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiConfiguration.getOpenAiKey());

        String url = openAiConfiguration.getOpenAiEndpoint();
        List<OpenAiDetailsDto> aiDetailsDtos = new ArrayList<>();
        aiDetailsDtos.add(new OpenAiDetailsDto( openAiConfiguration.getRole(), ask.getContent()));

        OpenAIRequestDto requestBody = new OpenAIRequestDto(openAiConfiguration.getOpenAiModel(), aiDetailsDtos,  openAiConfiguration.getOpenAiTemperature());

        HttpEntity<OpenAIRequestDto> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<OpenAiObjectDto> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, OpenAiObjectDto.class);

        assert responseEntity.getStatusCode() == HttpStatus.OK;

        return requestEntity.getBody().getMessages().get(0).getContent();
    }*/

    public Object getOpenAiRequest(OpenAiMessageDto ask) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiConfiguration.getOpenAiKey());

        String url = openAiConfiguration.getOpenAiEndpoint();
        List<OpenAiDetailsDto> aiDetailsDtos = new ArrayList<>();
        aiDetailsDtos.add(new OpenAiDetailsDto( openAiConfiguration.getRole(), ask.getContent()));

        OpenAIRequestDto requestBody = new OpenAIRequestDto(openAiConfiguration.getOpenAiModel(), aiDetailsDtos,  openAiConfiguration.getOpenAiTemperature(), openAiConfiguration.getMaxTokens());

        HttpEntity<OpenAIRequestDto> requestEntity = new HttpEntity<>(requestBody, headers);
        OpenAiObjectDto responseEntity = restTemplate.postForObject(url, requestEntity, OpenAiObjectDto.class);

        return responseEntity.getChoices().get(0).getMessage();
    }
}
