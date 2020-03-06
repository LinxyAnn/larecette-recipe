package com.larecette.recipe.service;


import com.larecette.recipe.dto.RecipeInsideDto;
import com.larecette.recipe.dto.RecipeOutDto;

import java.util.List;
import java.util.UUID;

public interface RecipeService {

    RecipeOutDto getRecipeDtoById(UUID recipeId);

    List<RecipeOutDto> getAllRecipe();

    RecipeOutDto createRecipe(RecipeInsideDto recipeInsideDto);

    RecipeOutDto updateRecipe(RecipeInsideDto recipeInsideDto, UUID recipeId);

     Boolean deleteRecipe(UUID recipeId);

}
