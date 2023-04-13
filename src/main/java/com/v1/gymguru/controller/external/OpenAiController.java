package com.v1.gymguru.controller.external;

import com.v1.gymguru.external.api.openai.OpenAiMessage;
import com.v1.gymguru.external.api.facade.OpenAiFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/gymguru/openai")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OpenAiController {
    private final OpenAiFacade openAiFacade;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OpenAiMessage> generateResponse(@RequestBody OpenAiMessage openAiMessage) {
        return ResponseEntity.ok(openAiFacade.getResponse(openAiMessage));
    }
}
