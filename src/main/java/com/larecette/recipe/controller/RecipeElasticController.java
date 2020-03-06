package com.larecette.recipe.controller;

import com.larecette.recipe.model.RecipeElastic;
import com.larecette.recipe.service.RecipeElasticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("recipe")
@Slf4j
public class RecipeElasticController {
    @Autowired
    private RecipeElasticService recipeElasticService;

    @GetMapping
    public ResponseEntity<List<RecipeElastic>> detAllRecipe() {
        return new ResponseEntity<>(recipeElasticService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<RecipeElastic> getRecipeById(@PathVariable("id") UUID id) {
        log.info("in GET mapping {}",id);
        RecipeElastic recipeElastic = recipeElasticService.findById(id);
        if(recipeElastic == null){
            log.info("in GET mapping, product not found by id {}",id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        recipeElastic.setId(id.toString());
        log.info("Recipe Elastic Entity find: " + recipeElastic);

        return new ResponseEntity<>(recipeElastic,HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity<RecipeElastic> createRecipe(@PathVariable("id") String recipeId, @RequestBody RecipeElastic recipeElastic) {
        log.info("recipeElastic: " + recipeElastic);
        recipeElastic.setId(recipeId.toString());
        log.info("Recipe Elastic Entity create: " + recipeElastic.toString());
        return new ResponseEntity<>(recipeElasticService.createRecipe(recipeElastic), HttpStatus.CREATED);
    }

    }
