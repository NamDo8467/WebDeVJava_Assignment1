package com.app.recipe_app.controller;

import com.app.recipe_app.entity.Recipe;

import com.app.recipe_app.entity.User;
import com.app.recipe_app.service.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;

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
            try {
                response.sendRedirect("/user/login");
            }catch (IOException e){
                modelAndView.setViewName("error.html");
            }
        }
        List<Recipe> recipes = this.recipeService.getAllRecipeByUserId(userCredentials);
        modelAndView.addObject("recipes", recipes);
        modelAndView.setViewName("view_recipe.html");
        modelAndView.addObject("recipe", new Recipe());
        return modelAndView;

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
            modelAndView.setViewName("create_recipe.html");
            modelAndView.addObject("recipe", recipe);
            return modelAndView;
        }
        return null;
    }

    @PostMapping("/addRecipe")
    public ModelAndView addNewRecipe(@ModelAttribute Recipe recipe,
                             @CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                             HttpServletResponse response) {
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

    @PostMapping("/searchRecipe")
    public ModelAndView searchRecipe(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                                     @ModelAttribute Recipe recipe,
                                     HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        if(userCredentials == 0){
            try{
                response.sendRedirect("/user/login");
                return null;
            }catch(IOException e){
                modelAndView.setViewName("error.html");
                return null;
            }

        }
        modelAndView.setViewName("search_recipe.html");
        List<Recipe> returnedRecipes = this.recipeService.searchAllRecipesByTitle(recipe.getTitle());
        if(returnedRecipes.size() > 0){
            modelAndView.addObject("returnedRecipes", returnedRecipes);

        }else{

            modelAndView.addObject("error", "No recipe found with that title");
        }
        modelAndView.addObject("recipe", new Recipe());
        return modelAndView;
    }


    @GetMapping("/edit/{id}")
    public ModelAndView editView(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                                 HttpServletResponse response,
                                 @PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
//        System.out.println(id);
        if(userCredentials == 0){
            try {
                response.sendRedirect("/user/login");
            }catch (IOException e){
                modelAndView.setViewName("error.html");
            }
        }else{
            Optional<Recipe> editRecipe = recipeService.getRecipeById(id);
            if(editRecipe.isPresent()){
                modelAndView.addObject("editRecipe", editRecipe.get());
                modelAndView.setViewName("edit_recipe.html");
                modelAndView.addObject("recipe", new Recipe());
            }else{
                modelAndView.setViewName("error.html");
            }

        }

        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteView(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                                 HttpServletResponse response,
                                 @PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
//        System.out.println(id);
        if(userCredentials == 0){
            try {
                response.sendRedirect("/user/login");
            }catch (IOException e){
                modelAndView.setViewName("error.html");
            }
        }else{
            Optional<Recipe> deleteRecipe = recipeService.getRecipeById(id);
            if(deleteRecipe.isPresent()){
                modelAndView.addObject("deleteRecipe", deleteRecipe.get());
                modelAndView.setViewName("delete_recipe.html");
                modelAndView.addObject("recipe", new Recipe());
            }else{
                modelAndView.setViewName("error.html");
            }

        }

        return modelAndView;
    }
    @PostMapping("/editRecipe")
    public ModelAndView postEdit(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                                 HttpServletResponse response,
                                 @ModelAttribute Recipe recipe) {
        ModelAndView modelAndView = new ModelAndView();
//        System.out.println(recipe);
//        System.out.println(id);
        if(userCredentials == 0){
            try{
                response.sendRedirect("/user/login");
            }catch(IOException e){
                modelAndView.setViewName("error.html");
            }
        }else{
            try {
                Optional<Recipe> recipeFromDatabase = recipeService.getRecipeById(recipe.getId());
                if(recipeFromDatabase.isPresent()){
                    recipeFromDatabase.get().setTitle(recipe.getTitle());
                    recipeFromDatabase.get().setDescription(recipe.getDescription());
                    recipeService.addNewRecipe(recipeFromDatabase.get());
                    response.sendRedirect("/recipe/all");
                    return null;
                }else{
                    modelAndView.setViewName("error.html");
                }

            }catch (IOException e){
                modelAndView.setViewName("home.html");
            }
        }

        return modelAndView;
    }
    @PostMapping("/deleteRecipe")
    public ModelAndView delete(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                                 HttpServletResponse response,
                                 @ModelAttribute Recipe recipe) {
        ModelAndView modelAndView = new ModelAndView();
        if(userCredentials == 0){
            try{
                response.sendRedirect("/user/login");
            }catch(IOException e){
                modelAndView.setViewName("error.html");
            }
        }else{
            try {
                Optional<Recipe> recipeFromDatabase = recipeService.getRecipeById(recipe.getId());
                if(recipeFromDatabase.isPresent()){
                    recipeFromDatabase.get().setTitle(recipe.getTitle());
                    recipeFromDatabase.get().setDescription(recipe.getDescription());
                    recipeService.deleteRecipe(recipeFromDatabase.get());
                    response.sendRedirect("/recipe/all");
                    return null;
                }else{
                    modelAndView.setViewName("error.html");
                }

            }catch (IOException e){
                modelAndView.setViewName("home.html");
            }
        }

        return modelAndView;
    }

}
