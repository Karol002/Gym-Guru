package com.v1.gymguru.openai;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpenAiDetailsDto {
     private String role;
     private String content;
}
