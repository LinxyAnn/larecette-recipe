package com.larecette.recipe.service;

import com.larecette.recipe.dto.IngredientInsideDto;
import com.larecette.recipe.dto.RecipeInsideDto;
import com.larecette.recipe.dto.RecipeOutDto;
import com.larecette.recipe.mapper.IngredientMapper;
import com.larecette.recipe.mapper.RecipeMapper;
import com.larecette.recipe.model.IngredientSpanner;
import com.larecette.recipe.model.Product;
import com.larecette.recipe.model.RecipeSpanner;
import com.larecette.recipe.repos.IngredientRepository;
import com.larecette.recipe.repos.RecipeSpannerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RecipeServiceSpannerImpl implements RecipeService {


    @Autowired
    private RecipeSpannerRepository recipeSpannerRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Override
    public RecipeOutDto getRecipeDtoById(UUID recipeId) {
        RecipeSpanner recipeSpanner = recipeSpannerRepository.findByRecipeId(recipeId);
        return recipeMapper.convertToRecipeQueryDto(recipeSpanner);
    }

    @Override
    public List<RecipeOutDto> getAllRecipe() {
        List<RecipeSpanner> recipeSpannerList = recipeSpannerRepository.findAll();
        return recipeSpannerList.stream().map(recipeMapper::convertToRecipeQueryDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RecipeOutDto createRecipe(RecipeInsideDto recipeInsideDto) {
        RecipeSpanner recipeSpanner = recipeMapper.convertToRecipeEntity(recipeInsideDto);
        List<IngredientSpanner> ingredientSpannerList = createIngredients(recipeInsideDto);
        recipeSpanner.setDifficulty(calculateDifficulty(recipeInsideDto));
        recipeSpanner.setCalories(calculateCalories(ingredientSpannerList));
        RecipeSpanner recipeSpannerNew = recipeSpannerRepository.saveAndFlush(recipeSpanner);
        ingredientSpannerList.forEach(ingredient -> ingredient.setRecipeId(recipeSpannerNew.getRecipeId().toString()));
        ingredientRepository.saveAll(ingredientSpannerList);
        recipeSpannerNew.setIngredientSpannerList(ingredientSpannerList);
        log.info("Create recipe: " + recipeSpannerNew);
        return recipeMapper.convertToRecipeQueryDto(recipeSpannerNew);
    }

    @Override
    @Transactional
    public RecipeOutDto updateRecipe(RecipeInsideDto recipeInsideDto, UUID recipeId) {
        RecipeSpanner recipeSpannerFromDB = recipeSpannerRepository.findByRecipeId(recipeId);
        if (!recipeSpannerRepository.existsById(recipeId)) {
            return null;
        }
        ingredientRepository.deleteAllByRecipeId(recipeId.toString());

        List<IngredientSpanner> ingredientSpannerList = createIngredients(recipeInsideDto);
        ingredientSpannerList.forEach(ingredient -> ingredient.setRecipeId(recipeSpannerFromDB.getRecipeId().toString()));
        ingredientRepository.saveAll(ingredientSpannerList);

        RecipeSpanner recipeSpanner = recipeMapper.convertToRecipeEntity(recipeInsideDto);
        recipeSpanner.setDifficulty(calculateDifficulty(recipeInsideDto));
        recipeSpanner.setCalories(calculateCalories(ingredientSpannerList));
        recipeSpanner.setIngredientSpannerList(ingredientSpannerList);
        recipeSpanner.setRecipeId(recipeId);
        RecipeOutDto recipeOutDto = recipeMapper.convertToRecipeQueryDto(recipeSpannerRepository.save(recipeSpanner));
        log.info("Update recipe: " + recipeOutDto);
        return recipeOutDto;
    }

    @Override
    public Boolean deleteRecipe(UUID recipeId) {
        Boolean exists = recipeSpannerRepository.existsById(recipeId);
        if (exists) {
            recipeSpannerRepository.deleteById(recipeId);
        }
        log.info("Recipe with id " + recipeId + " deleted");
        return exists;
    }

    private List<IngredientSpanner> createIngredients(RecipeInsideDto recipeInsideDto) {
        List<IngredientSpanner> ingredientSpannerList = new ArrayList<>();
        recipeInsideDto.getIngredientList().forEach(ingredientInsideDto -> {
            Map<String, String> uriVariables = new HashMap<>();
            uriVariables.put("id", ingredientInsideDto.getProductId());
            ResponseEntity<Product> responseEntity = new RestTemplate().getForEntity(
                    "http://localhost:8084/product/consistency/{id}", Product.class,
                    uriVariables);
            Product response = responseEntity.getBody();
            if (response != null) {
                ingredientSpannerList.add(ingredientMapper.convertToIngredientFromProduct(response, ingredientInsideDto.getAmount()));
            }
        });
        return ingredientSpannerList;
    }

    private Double calculateCalories(List<IngredientSpanner> ingredientSpannerList) {
        List<Double> caloriesList = new ArrayList<>();
        ingredientSpannerList.forEach(ingredientSpanner -> caloriesList.add(ingredientSpanner.getCalories()));
        return caloriesList.stream().reduce(Double::sum).orElse(null);
    }

    private Integer calculateDifficulty(RecipeInsideDto recipeInsideDto) {
        double countIngredients = recipeInsideDto.getIngredientList().size();
        double time = recipeInsideDto.getTime();
        int difficulty = (int) Math.round((time * 4.2 + countIngredients * 50) / 200);
        return difficulty;
    }

}
