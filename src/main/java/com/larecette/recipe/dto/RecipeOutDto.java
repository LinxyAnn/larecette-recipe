package com.larecette.recipe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeOutDto {
    private UUID recipeId;
    private String recipeName;
    private String description;
    private Integer time;
    private Integer difficulty;
    private String cuisine;
    private Double calories;

    List<IngredientOutDto> ingredientList;

}

