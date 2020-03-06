package com.larecette.recipe.repos;

import com.larecette.recipe.model.IngredientSpanner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface IngredientRepository extends JpaRepository<IngredientSpanner, UUID> {
    @Override
    List<IngredientSpanner> findAll();

    void deleteAllByRecipeId(String recipeId);
}
