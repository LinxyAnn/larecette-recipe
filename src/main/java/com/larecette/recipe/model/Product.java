package com.larecette.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    private Double proteins;

    private Double carbohydrates;

    private Double fats;

    private Double calories;

    private String name;

    private String measureUnit;

    private UUID id;
}
