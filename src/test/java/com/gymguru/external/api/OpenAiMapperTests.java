package com.gymguru.external.api;

import com.gymguru.external.api.openai.OpenAIRequest;
import com.gymguru.external.api.openai.OpenAiConfiguration;
import com.gymguru.external.api.openai.OpenAiMapper;
import com.gymguru.external.api.openai.OpenAiMessage;
import com.gymguru.external.api.openai.dto.OpenAiBoardDto;
import com.gymguru.external.api.openai.dto.OpenAiDetailsDto;
import com.gymguru.external.api.openai.dto.OpenAiResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class OpenAiMapperTests {

    @Autowired
    private OpenAiMapper openAiMapper;

    @Autowired
    private OpenAiConfiguration openAiConfiguration;

    @Test
    public void testMapToOpenAiRequest() {
        //Given
        OpenAiMessage openAiMessage = new OpenAiMessage("content");

        //When
        OpenAIRequest openAIRequest = openAiMapper.mapToOpenAiRequest(openAiMessage);

        //Then
        assertEquals(openAiConfiguration.getOpenAiModel(), openAIRequest.getModel());
        assertEquals(1, openAIRequest.getMessages().size());
        assertEquals(openAiConfiguration.getRole(), openAIRequest.getMessages().get(0).getRole());
        assertEquals("content", openAIRequest.getMessages().get(0).getContent());
        assertEquals(openAiConfiguration.getOpenAiTemperature(), openAIRequest.getTemperature());
        assertEquals(openAiConfiguration.getMaxTokens(), openAIRequest.getMax_tokens());
    }

    @Test
    public void testMapToOpenAiDetailsDtoList() {
        //Given
        OpenAiMessage openAiMessage = new OpenAiMessage("content");

        //When
        List<OpenAiDetailsDto> openAiDetailsDtoList = openAiMapper.mapToOpenAiDetailsDtoList(openAiMessage);

        //Then
        assertEquals(1, openAiDetailsDtoList.size());
        assertEquals(openAiConfiguration.getRole(), openAiDetailsDtoList.get(0).getRole());
        assertEquals("content", openAiDetailsDtoList.get(0).getContent());
    }

    @Test
    public void testMapToOpenAiMessageDto() {
        //Given
        OpenAiResponseDto openAiResponseDto = new OpenAiResponseDto(new OpenAiMessage("content"));
        OpenAiBoardDto openAiBoardDto = new OpenAiBoardDto(Collections.singletonList(openAiResponseDto));

        //When
        OpenAiMessage openAiMessage = openAiMapper.mapToOpenAiMessageDto(openAiBoardDto);

        //Then
        assertEquals("content", openAiMessage.getContent());
    }

    @Test
    public void testMapToOpenAiMessageDtoWhenChoicesListIsEmpty() {
        //Given
        OpenAiBoardDto openAiBoardDto = new OpenAiBoardDto(Collections.emptyList());

        //When
        OpenAiMessage openAiMessage = openAiMapper.mapToOpenAiMessageDto(openAiBoardDto);

        //Then
        assertNull(openAiMessage);
    }
}
