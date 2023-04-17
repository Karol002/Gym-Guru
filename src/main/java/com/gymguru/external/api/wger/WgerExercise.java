package com.gymguru.external.api.wger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WgerExercise {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
}
