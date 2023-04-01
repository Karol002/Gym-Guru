package com.v1.gymguru.controller.external;

import com.v1.gymguru.openai.OpenAiClient;
import com.v1.gymguru.openai.OpenAiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/gymguru/openai")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OpenAiController {
    private final OpenAiClient openAiClient;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OpenAiResponseDto> generateResponse(@RequestBody OpenAiResponseDto openAiResponseDto) {
        return ResponseEntity.ok(openAiClient.getOpenAiRequest(openAiResponseDto));
    }
}
