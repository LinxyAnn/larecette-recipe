package com.larecette.recipe.repos;

import com.larecette.recipe.model.RecipeSpanner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecipeSpannerRepository extends JpaRepository<RecipeSpanner, UUID> {
    @Override
    List<RecipeSpanner> findAll();

    RecipeSpanner findByRecipeId(UUID recipeId);

}
