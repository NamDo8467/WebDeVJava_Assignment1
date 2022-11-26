package com.app.recipe_app.service;

import com.app.recipe_app.entity.Recipe;
import com.app.recipe_app.entity.User;
import com.app.recipe_app.repository.RecipeRepository;
import com.app.recipe_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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


    public List<Recipe> getAllRecipeByUserId(Long userId) {
        return recipeRepository.getAllByUserId(userId);
    }

    public void addNewRecipe(Recipe recipe) {

        recipeRepository.save(recipe);

    }

    public List<Recipe> searchAllRecipesByTitle(String title) {
        List<Recipe> recipes = recipeRepository.getAllByTitle(title);
        return recipes;
    }
    public void deleteRecipe(Recipe recipe){
        recipeRepository.deleteById(recipe.getId());
    }

    public Optional<Recipe> getRecipeById(Long id){
        return recipeRepository.findById(id);
    }

}
