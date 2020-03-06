package com.larecette.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Document(indexName = "recipes", type = "recipe")
public class IngredientElastic {

    private UUID ingredientId;

    private String recipeId;

    private String productId;

    private String name;

    private String measureUnit;

    private Double calories;

    private Double amount;
}
