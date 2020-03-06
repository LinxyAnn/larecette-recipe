package com.larecette.recipe.repos;

import com.larecette.recipe.model.RecipeElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;
import java.util.UUID;

public interface RecipeElasticRepository extends ElasticsearchCrudRepository<RecipeElastic, UUID> {
    @Override
    List<RecipeElastic> findAll();
}
