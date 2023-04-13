package com.v1.gymguru.external.api.wger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WgerCategoryBoardDto {
    @JsonProperty("results")
    private List<WgerCategoryDto> wgerCategoryDtos;
}
