package com.app.recipe_app.service;

import com.app.recipe_app.entity.Recipe;
import com.app.recipe_app.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipe(){
        return recipeRepository.findAll();
    }

    public void addNewRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public Optional<Recipe> searchRecipe(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return recipe;
    }
}
