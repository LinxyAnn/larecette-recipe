package com.larecette.recipe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.UUID;


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientOutDto {
    private UUID ingredientId;
    private String recipeId;
    private String productId;
    private String name;
    private String measureUnit;
    private Double calories;
    private Double amount;
}
