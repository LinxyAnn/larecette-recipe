package com.larecette.recipe.mapper;

import com.larecette.recipe.dto.IngredientOutDto;
import com.larecette.recipe.dto.RecipeInsideDto;
import com.larecette.recipe.dto.RecipeOutDto;
import com.larecette.recipe.model.RecipeSpanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeMapper {
    @Autowired
    private IngredientMapper ingredientMapper;

    public RecipeOutDto convertToRecipeQueryDto(RecipeSpanner recipeSpanner) {
        RecipeOutDto recipeOutDto = new RecipeOutDto();
        recipeOutDto.setRecipeId(recipeSpanner.getRecipeId());
        recipeOutDto.setRecipeName(recipeSpanner.getRecipeName());
        recipeOutDto.setDescription(recipeSpanner.getDescription());
        recipeOutDto.setTime(recipeSpanner.getTime());
        recipeOutDto.setDifficulty(recipeSpanner.getDifficulty());
        recipeOutDto.setCuisine(recipeSpanner.getCuisine());
        recipeOutDto.setCalories(recipeSpanner.getCalories());
        List<IngredientOutDto> ingredientOutDtoList = recipeSpanner.getIngredientSpannerList().stream().map(ingredientMapper::convertToIngredientDto).collect(Collectors.toList());
        recipeOutDto.setIngredientList(ingredientOutDtoList);
        return recipeOutDto;
    }

    public RecipeSpanner convertToRecipeEntity(RecipeInsideDto recipeInsideDto) {
        RecipeSpanner recipeSpanner = new RecipeSpanner();
        recipeSpanner.setRecipeName(recipeInsideDto.getRecipeName());
        recipeSpanner.setDescription(recipeInsideDto.getDescription());
        recipeSpanner.setTime(recipeInsideDto.getTime());
        recipeSpanner.setCuisine(recipeInsideDto.getCuisine());

        return recipeSpanner;
    }
}
