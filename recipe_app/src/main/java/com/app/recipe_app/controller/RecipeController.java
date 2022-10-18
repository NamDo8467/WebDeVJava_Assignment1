package com.app.recipe_app.controller;

import com.app.recipe_app.entity.Recipe;
import com.app.recipe_app.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @GetMapping("/all")
    public List<Recipe> findAll() {
        return this.recipeService.getAllRecipe();
    }

    @PostMapping("/createRecipe")
    public void addNewRecipe(@RequestBody Recipe recipe) {
//        System.out.print(recipe);
        this.recipeService.addNewRecipe(recipe);
    }

    @GetMapping("/searchRecipe/{recipeId}")
    public Optional<Recipe> searchRecipe(@PathVariable("recipeId") Long id){
        Optional<Recipe> recipe = this.recipeService.searchRecipe(id);
        return recipe;
    }


}
