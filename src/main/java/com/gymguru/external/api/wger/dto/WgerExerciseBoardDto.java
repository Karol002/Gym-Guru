package com.gymguru.external.api.wger.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gymguru.external.api.wger.WgerExercise;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WgerExerciseBoardDto {
    @JsonProperty("results")
    private List<WgerExercise> wgerExercises;
}
