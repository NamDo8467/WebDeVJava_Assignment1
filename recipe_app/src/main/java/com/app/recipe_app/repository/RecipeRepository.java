package com.app.recipe_app.repository;

import com.app.recipe_app.entity.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository <Recipe, Long> {
}
