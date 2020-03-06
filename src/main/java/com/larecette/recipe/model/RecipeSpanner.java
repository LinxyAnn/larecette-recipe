package com.larecette.recipe.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Table(name = "RECIPE")
@Data
@NoArgsConstructor
@Entity
public class RecipeSpanner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(name = "RECIPE_ID")
    private UUID recipeId;

    @NotNull
    @Column(name = "RECIPE_NAME")
    private String recipeName;

    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "TIME")
    private Integer time;

    @NotNull
    @Column(name = "DIFFICULTY")
    private Integer difficulty;

    @NotNull
    @Column(name = "CUISINE")
    private String cuisine;

    @NotNull
    @Column(name = "CALORIES")
    private Double calories;

    @OneToMany(mappedBy = "recipeId", cascade = CascadeType.ALL)
    private List<IngredientSpanner> ingredientSpannerList = new ArrayList<>();

}
