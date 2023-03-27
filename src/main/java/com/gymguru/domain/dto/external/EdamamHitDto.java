package com.gymguru.domain.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EdamamHitDto {

    @JsonProperty("hits")
    private List<EdamamRecipeDto> edamamRecipeDto;
}