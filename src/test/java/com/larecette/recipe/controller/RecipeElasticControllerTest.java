package com.larecette.recipe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.larecette.recipe.model.IngredientElastic;
import com.larecette.recipe.model.RecipeElastic;
import com.larecette.recipe.service.RecipeElasticService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RecipeElasticControllerTest {

    public static final String MAIN_ENDPOINT = "/recipe/";
    public static final UUID RANDOM_ID = UUID.randomUUID();

    ArrayList<IngredientElastic> ingredientElastics;

    RecipeElastic recipeElastic;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeElasticService recipeService;

    @Test
    void shouldReturnRecipeAnd200() throws Exception {
        Mockito.when(recipeService.findById(RANDOM_ID))
                .thenReturn(recipeElastic);
        this.mockMvc.perform(get(MAIN_ENDPOINT + RANDOM_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("onion")))
                .andExpect(content().string(containsString("tomato")));
    }

    @Test
    void shouldReturn404() throws Exception {
        Mockito.when(recipeService.findById(RANDOM_ID))
                .thenReturn(null);
        this.mockMvc.perform(get(MAIN_ENDPOINT + RANDOM_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateRecipeAndReturn201() throws Exception {
        this.mockMvc.perform(post(MAIN_ENDPOINT + RANDOM_ID)
                .content(asJsonString(recipeElastic))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @BeforeEach
    void setUp() {

        IngredientElastic carrot = new IngredientElastic();
        carrot.setAmount(2.0);
        carrot.setCalories(120.0);
        carrot.setIngredientId(UUID.randomUUID());
        carrot.setMeasureUnit("item");
        carrot.setName("carrot");
        carrot.setProductId(carrot.getIngredientId().toString());
        carrot.setRecipeId(RANDOM_ID.toString());

        IngredientElastic onion = new IngredientElastic();
        onion.setAmount(1.0);
        onion.setCalories(12.0);
        onion.setIngredientId(UUID.randomUUID());
        onion.setMeasureUnit("item");
        onion.setName("onion");
        onion.setProductId(onion.getIngredientId().toString());
        onion.setRecipeId(RANDOM_ID.toString());

        IngredientElastic tomato = new IngredientElastic();
        tomato.setAmount(1.0);
        tomato.setCalories(35.0);
        tomato.setIngredientId(UUID.randomUUID());
        tomato.setMeasureUnit("item");
        tomato.setName("tomato");
        tomato.setProductId(tomato.getIngredientId().toString());
        tomato.setRecipeId(RANDOM_ID.toString());

        ingredientElastics = new ArrayList<IngredientElastic>();
        ingredientElastics.add(tomato);
        ingredientElastics.add(carrot);
        ingredientElastics.add(onion);

        recipeElastic = new RecipeElastic();
        recipeElastic.setIngredientList(ingredientElastics);
        recipeElastic.setCalories(Math.random());
        recipeElastic.setCuisine("Chinese");
        recipeElastic.setDescription("description");
        recipeElastic.setDifficulty(5);
        recipeElastic.setRecipeName("Salad");
        recipeElastic.setTime(15);
        recipeElastic.setRecipeId(RANDOM_ID.toString());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}