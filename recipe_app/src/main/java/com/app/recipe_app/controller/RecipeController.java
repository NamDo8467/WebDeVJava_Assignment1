package com.app.recipe_app.controller;

import com.app.recipe_app.entity.Recipe;

import com.app.recipe_app.service.RecipeService;
import com.app.recipe_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
        private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;

    }
    @GetMapping("/all")
    public List<Recipe> findAll(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials) {
        if(userCredentials == 0){
            System.out.println("No access from yuour end");
            return null;
        }
        return this.recipeService.getAllRecipeByUserId(userCredentials);
    }

    @PostMapping("/addRecipe")
    public void addNewRecipe(@RequestBody Recipe recipe,
                             @CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                             HttpServletResponse response) {
//        System.out.print(recipe.getUserId());
        if(userCredentials == 0){
            try{
                System.out.println("No access");
//                response.sendRedirect("/user/login");
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        this.recipeService.addNewRecipe(recipe);
    }


    @GetMapping("/searchRecipe/{recipeId}")
    public Optional<Recipe> searchRecipe(@PathVariable("recipeId") Long recipeId,
                                         @CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials){
        if(userCredentials == 0){
            System.out.println("No access");
            return null;
        }
        Optional<Recipe> recipe = this.recipeService.searchRecipe(userCredentials, recipeId);
        return recipe;
    }


}
