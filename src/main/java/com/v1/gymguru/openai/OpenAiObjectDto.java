package com.v1.gymguru.openai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAiObjectDto {
    @JsonProperty("choices")
    private List<OpenAiResponseDto> choices;
}
