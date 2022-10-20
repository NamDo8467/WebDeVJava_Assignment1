package com.app.recipe_app.controller;


import com.app.recipe_app.entity.Recipe;
import com.app.recipe_app.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    private RecipeRepository recipeRepository;

    @PostMapping("/save")
    public void save(@RequestBody Recipe recipe) {
        System.out.print(recipe);
        this.recipeRepository.save(recipe);
    }

    @GetMapping("/findAll")
    public List findAll() {
        return (List) this.recipeRepository.findAll();
    }
}
