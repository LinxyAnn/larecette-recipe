package com.larecette.recipe.controller;

import com.larecette.recipe.dto.RecipeInsideDto;
import com.larecette.recipe.dto.RecipeOutDto;
import com.larecette.recipe.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("recipe/consistency")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeOutDto>> getRecipeList() {
        return new ResponseEntity<>(recipeService.getAllRecipe(), HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<RecipeOutDto> recipeById(@PathVariable("id") UUID recipeId) {
        RecipeOutDto recipeOutDto = recipeService.getRecipeDtoById(recipeId);
        if (recipeOutDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(recipeOutDto, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<RecipeOutDto> createRecipe(@RequestBody RecipeInsideDto recipeInsideDto) {
        return new ResponseEntity<>(recipeService.createRecipe(recipeInsideDto), HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    public ResponseEntity<RecipeOutDto> updateRecipe(
            @PathVariable("id") UUID recipeId,
            @RequestBody RecipeInsideDto recipeInsideDto
    ) {
        RecipeOutDto recipeOutDtoResult = recipeService.updateRecipe(recipeInsideDto,recipeId);
        if (recipeOutDtoResult == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(recipeService.updateRecipe(recipeInsideDto, recipeId), HttpStatus.OK);
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Long> deleteRecipe(@PathVariable("id") UUID recipeId) {
        Boolean existed = recipeService.deleteRecipe(recipeId);
        if (existed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
