package com.larecette.recipe.model;

import com.google.cloud.spanner.hibernate.Interleaved;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Table(name = "INGREDIENT")
@Data
@Entity
@NoArgsConstructor
@Interleaved(parentEntity = RecipeSpanner.class, cascadeDelete = true)
public class IngredientSpanner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(name = "INGREDIENT_ID")
    private UUID ingredientId;

    @NotNull
    @Column(name = "RECIPE_ID")
    private String recipeId;

    @NotNull
    @Column(name = "PRODUCT_ID")
    private String productId;

    @NotNull
    @Column(name = "NAME")
    private String name;


    @NotNull
    @Column(name = "MEASURE_UNIT")
    private String measureUnit;

    @NotNull
    @Column(name = "CALORIES")
    private Double calories;

    @NotNull
    @Column(name = "AMOUNT")
    private Double amount;
}
