package com.v1.gymguru.wger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WgerExerciseDto {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
}
