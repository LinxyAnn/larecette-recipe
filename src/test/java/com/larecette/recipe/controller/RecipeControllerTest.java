package com.larecette.recipe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.larecette.recipe.dto.IngredientInsideDto;
import com.larecette.recipe.dto.IngredientOutDto;
import com.larecette.recipe.dto.RecipeInsideDto;
import com.larecette.recipe.dto.RecipeOutDto;

import com.larecette.recipe.service.RecipeServiceSpannerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {

    public static final String MAIN_ENDPOINT = "/recipe/consistency/";
    public static final UUID RANDOM_ID = UUID.randomUUID();

    List<IngredientOutDto> ingredientsOutDto;
    List<IngredientInsideDto> ingredientsInsideDto;
    RecipeOutDto recipeOutDto;
    RecipeInsideDto recipeInsideDto;
    IngredientInsideDto ingredientInsideDto;
    IngredientOutDto ingredientOutDto;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeServiceSpannerImpl recipeService;

    @Test
    void shouldReturnRecipeAnd200() throws Exception {
        Mockito.when(recipeService.getRecipeDtoById(RANDOM_ID))
                .thenReturn(recipeOutDto);
        this.mockMvc.perform(get(MAIN_ENDPOINT + RANDOM_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("onion")))
                .andExpect(content().string(containsString("tomato")));
    }

    @Test
    void shouldReturn404() throws Exception {
        Mockito.when(recipeService.getRecipeDtoById(RANDOM_ID))
                .thenReturn(null);
        this.mockMvc.perform(get(MAIN_ENDPOINT + RANDOM_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateRecipeAndReturn201() throws Exception {
        this.mockMvc.perform(post(MAIN_ENDPOINT )
                .content(asJsonString(recipeInsideDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void shouldUpdateRecipeAndReturn200() throws Exception {

        Mockito.when(recipeService.updateRecipe(recipeInsideDto, RANDOM_ID))
                .thenReturn(recipeOutDto);

        this.mockMvc.perform(patch(MAIN_ENDPOINT + RANDOM_ID )
                .content(asJsonString(recipeInsideDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("tomato")))
                .andExpect(content().string(containsString("onion")))
                .andExpect(content().string(containsString(RANDOM_ID.toString())));
    }

    @Test
    public void shouldReturn404OnUpdateIfResourceNotFound() throws Exception {

        Mockito.when(recipeService.updateRecipe(recipeInsideDto, RANDOM_ID))
                .thenReturn(null);

        this.mockMvc.perform(patch(MAIN_ENDPOINT + RANDOM_ID )
                .content(asJsonString(recipeInsideDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteRecipeAndReturn204() throws Exception {
        Mockito.when(recipeService.deleteRecipe(RANDOM_ID))
                .thenReturn(true);
        this.mockMvc.perform(delete(MAIN_ENDPOINT + RANDOM_ID )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404OnDeleteIfResourceNotFound() throws Exception {
        Mockito.when(recipeService.deleteRecipe(RANDOM_ID))
                .thenReturn(false);
        this.mockMvc.perform(delete(MAIN_ENDPOINT + RANDOM_ID )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @BeforeEach
    void setUp() {
        IngredientOutDto carrot = new IngredientOutDto();
        carrot.setAmount(2.0);
        carrot.setCalories(120.0);
        carrot.setIngredientId(UUID.randomUUID());
        carrot.setMeasureUnit("item");
        carrot.setName("carrot");
        carrot.setProductId(carrot.getIngredientId().toString());
        carrot.setRecipeId(RANDOM_ID.toString());

        IngredientInsideDto carrotInsideDto = new IngredientInsideDto();
        carrotInsideDto.setAmount(carrot.getAmount());
        carrotInsideDto.setCalories(carrot.getCalories());
        carrotInsideDto.setIngredientId(carrot.getIngredientId());
        carrotInsideDto.setMeasureUnit(carrot.getMeasureUnit());
        carrotInsideDto.setName(carrot.getName());
        carrotInsideDto.setProductId(carrot.getIngredientId().toString());
        carrotInsideDto.setRecipeId(RANDOM_ID.toString());

        IngredientOutDto onion = new IngredientOutDto();
        onion.setAmount(1.0);
        onion.setCalories(12.0);
        onion.setIngredientId(UUID.randomUUID());
        onion.setMeasureUnit("item");
        onion.setName("onion");
        onion.setProductId(onion.getIngredientId().toString());
        onion.setRecipeId(RANDOM_ID.toString());

        IngredientInsideDto onionInsideDto = new IngredientInsideDto();
        onionInsideDto.setAmount(onion.getAmount());
        onionInsideDto.setCalories(onion.getCalories());
        onionInsideDto.setIngredientId(onion.getIngredientId());
        onionInsideDto.setMeasureUnit(onion.getMeasureUnit());
        onionInsideDto.setName(onion.getName());
        onionInsideDto.setProductId(onion.getIngredientId().toString());
        onionInsideDto.setRecipeId(RANDOM_ID.toString());

        IngredientOutDto tomato = new IngredientOutDto();
        tomato.setAmount(1.0);
        tomato.setCalories(35.0);
        tomato.setIngredientId(UUID.randomUUID());
        tomato.setMeasureUnit("item");
        tomato.setName("tomato");
        tomato.setProductId(tomato.getIngredientId().toString());
        tomato.setRecipeId(RANDOM_ID.toString());

        IngredientInsideDto tomatoInsideDto = new IngredientInsideDto();
        tomatoInsideDto.setAmount(tomato.getAmount());
        tomatoInsideDto.setCalories(tomato.getCalories());
        tomatoInsideDto.setIngredientId(tomato.getIngredientId());
        tomatoInsideDto.setMeasureUnit(tomato.getMeasureUnit());
        tomatoInsideDto.setName(tomato.getName());
        tomatoInsideDto.setProductId(tomato.getIngredientId().toString());
        tomatoInsideDto.setRecipeId(RANDOM_ID.toString());

        ingredientsOutDto = new ArrayList<>();
        ingredientsOutDto.add(tomato);
        ingredientsOutDto.add(carrot);
        ingredientsOutDto.add(onion);

        ingredientsInsideDto = new ArrayList<>();
        ingredientsInsideDto.add(tomatoInsideDto);
        ingredientsInsideDto.add(tomatoInsideDto);
        ingredientsInsideDto.add(tomatoInsideDto);


        recipeOutDto = new RecipeOutDto();
        recipeOutDto.setIngredientList(ingredientsOutDto);
        recipeOutDto.setCalories(Math.random());
        recipeOutDto.setCuisine("Chinese");
        recipeOutDto.setDescription("description");
        recipeOutDto.setDifficulty(5);
        recipeOutDto.setRecipeName("Salad");
        recipeOutDto.setTime(15);
        recipeOutDto.setRecipeId(RANDOM_ID);

        recipeInsideDto = new RecipeInsideDto();
        recipeInsideDto.setIngredientList(ingredientsInsideDto);
        recipeInsideDto.setCalories(recipeOutDto.getCalories());
        recipeInsideDto.setCuisine(recipeOutDto.getDescription());
        recipeInsideDto.setDescription(recipeOutDto.getDescription());
        recipeInsideDto.setDifficulty(recipeOutDto.getDifficulty());
        recipeInsideDto.setRecipeName(recipeOutDto.getRecipeName());
        recipeInsideDto.setTime(recipeOutDto.getTime());
        recipeInsideDto.setRecipeId(RANDOM_ID);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}