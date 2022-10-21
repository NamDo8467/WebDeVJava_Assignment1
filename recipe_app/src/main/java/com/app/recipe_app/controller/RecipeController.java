package com.app.recipe_app.controller;

import com.app.recipe_app.entity.Recipe;

import com.app.recipe_app.entity.User;
import com.app.recipe_app.service.RecipeService;
import com.app.recipe_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public ModelAndView findAll(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                                HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        if(userCredentials == 0){
//            System.out.println("No access");
//            return null;
            try {
                response.sendRedirect("/user/login");
            }catch (IOException e){
                modelAndView.setViewName("error.html");
            }
        }
        List<Recipe> recipes = this.recipeService.getAllRecipeByUserId(userCredentials);
        modelAndView.addObject("recipes", recipes);
        modelAndView.setViewName("view_recipe.html");
        return modelAndView;
//        return this.recipeService.getAllRecipeByUserId(userCredentials);
    }
    @GetMapping("/addRecipe")
    public ModelAndView addNewRecipe(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                                     HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        if(userCredentials == 0){
            try{
                response.sendRedirect("/user/login");
            }catch(IOException e){
                modelAndView.setViewName("error.html");
            }
        }else{
            User user = new User(userCredentials);
            Recipe recipe = new Recipe("", "", user);
            modelAndView.setViewName("add_recipe.html");
            modelAndView.addObject("recipe", recipe);
            return modelAndView;
        }
        return null;
    }

    @PostMapping("/addRecipe")
    public ModelAndView addNewRecipe(@ModelAttribute Recipe recipe,
                             @CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                             HttpServletResponse response) {
//        System.out.print(recipe.getUserId());
        ModelAndView modelAndView = new ModelAndView();
        if(userCredentials == 0){
            try{
                response.sendRedirect("/user/login");
            }catch(IOException e){
                modelAndView.setViewName("error.html");
            }
        }else{
            try{
                recipe.getUser().setId(userCredentials);
                this.recipeService.addNewRecipe(recipe);
//                modelAndView.setViewName("view_recipe.html");
                response.sendRedirect("/recipe/all");
            }catch(IOException e){
                modelAndView.setViewName("error.html");
            }
        }
        return null;


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
