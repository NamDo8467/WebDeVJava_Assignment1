package com.app.recipe_app.controller;

import com.app.recipe_app.entity.Meal;
import com.app.recipe_app.entity.Recipe;
import com.app.recipe_app.entity.User;
import com.app.recipe_app.service.MealService;
import com.app.recipe_app.service.RecipeService;
import com.app.recipe_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/meal")
public class MealController {
    private final MealService mealService;
    private final RecipeService recipeService;

    private final UserService userService;

    @Autowired
    public MealController(MealService mealService, RecipeService recipeService, UserService userService) {
        this.mealService = mealService;
        this.recipeService = recipeService;
        this.userService = userService;
    }


    @GetMapping("/all")
    public List<Meal> getAllMeals(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials)
    {
        if(userCredentials == 0L){
            System.out.println("No access");
            return null;
        }
        return this.mealService.getAllMeals(userCredentials);
    }

    @GetMapping("/viewMeal")
    public ModelAndView viewAllMeal(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                                    HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("recipe", new Recipe()); // This recipe is for the search
        if(userCredentials == 0){
            try{
                response.sendRedirect("/user/login");
                return null;
            }catch(IOException e){
                modelAndView.setViewName("error.html");
                return modelAndView;
            }
        }
        List<Meal> meals = mealService.getAllMeals(userCredentials);
//        for(Meal m : meals){
//            System.out.println(m.getRecipes());
//        }
        if(meals.size() == 0 ){
            modelAndView.addObject("noMeal", "There is not meal");
        }else{
            modelAndView.addObject("meals", meals);
        }
        modelAndView.setViewName("view_meal.html");
//        modelAndView.addObject();

        return modelAndView;
    }

    @GetMapping("/addNewMeal")
    public ModelAndView addNewMeal(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                                   HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        if(userCredentials == 0){
            try{
                response.sendRedirect("/user/login");
                return null;
            }catch(IOException e){
                modelAndView.setViewName("error.html");
                return modelAndView;
            }
        }
        List<Recipe> recipes = recipeService.getAllRecipeByUserId(userCredentials);

        String date = "";
        String mealName = "";
        String recipeId = "";


        modelAndView.setViewName("plan_meal.html");
        modelAndView.addObject("dateAdded", date);
        modelAndView.addObject("mealName", mealName);

        modelAndView.addObject("recipesA", recipes);

        modelAndView.addObject("recipeId", recipeId);
        modelAndView.addObject("recipe", new Recipe());
        return modelAndView;
    }
    @PostMapping("/addNewMeal")
    public ModelAndView addMeal(@ModelAttribute("mealName") String mealName,
                                @ModelAttribute("dateAdded") String dateAdded,
                                @ModelAttribute("recipeId") String recipeId,
                                @CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                                HttpServletResponse response) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        if(userCredentials == 0){
            try{
                response.sendRedirect("/user/login");
                return null;
            }catch(IOException e){
                modelAndView.addObject("recipe", new Recipe());
                modelAndView.setViewName("home.html");
                return modelAndView;
            }
        }
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateAdded);


        Meal meal = new Meal();
        meal.setMealName(mealName);
        meal.setDateAdded(date);

        Optional<Recipe> recipeMeal = recipeService.getRecipeById(Long.parseLong(recipeId));
        if(recipeMeal.isPresent()){
            List<Recipe> recipes = new ArrayList<Recipe>();
            recipes.add(recipeMeal.get());
            meal.setRecipes(recipes);

        }

        Optional<User> user = userService.getUserById(userCredentials);
        if(user.isPresent()){
            User userMeal = user.get();
            meal.setUser(userMeal);
        }
        mealService.addNewMeal(meal);

        try{
            response.sendRedirect("/user/home");
            return null;
        }catch(IOException e){
            modelAndView.addObject("recipe", new Recipe());
            modelAndView.setViewName("home.html");
            return modelAndView;
        }

    }

    @GetMapping("/edit/{id}")
    public ModelAndView addNewMeal(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                                   HttpServletResponse response,
                                   @PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();
        if(userCredentials == 0){
            try{
                response.sendRedirect("/user/login");
                return null;
            }catch(IOException e){
                modelAndView.setViewName("error.html");
                return modelAndView;
            }
        }
        List<Recipe> recipes = recipeService.getAllRecipeByUserId(userCredentials);

        String recipeId = "";

        Optional<Meal> editedMeal = mealService.getMealById(id);

        String date = "";
        String mealName = "";
        if(editedMeal.isPresent()){
            modelAndView.setViewName("edit_meal.html");
            modelAndView.addObject("editedMeal", editedMeal.get());
            modelAndView.addObject("dateAddedString", date);
            modelAndView.addObject("mealName", mealName);
            modelAndView.addObject("recipesA", recipes);
            modelAndView.addObject("recipeId", recipeId);
            modelAndView.addObject("recipe", new Recipe());
        }else{
            modelAndView.setViewName("error.html");
        }
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editMeal(
//            @ModelAttribute("mealName") String mealName,
//                                @ModelAttribute("dateAddedString") String dateAddedString,
                                @ModelAttribute("recipeId") String recipeId,
                                @ModelAttribute("editedMeal") Meal editedMeal,
                                @CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                                HttpServletResponse response) throws ParseException {


        ModelAndView modelAndView = new ModelAndView();
        if(userCredentials == 0){
            try{
                response.sendRedirect("/user/login");
                return null;
            }catch(IOException e){
                modelAndView.addObject("recipe", new Recipe());
                modelAndView.setViewName("home.html");
                return modelAndView;
            }
        }

        Optional<Recipe> recipeMeal = recipeService.getRecipeById(Long.parseLong(recipeId));
        if(recipeMeal.isPresent()){
            List<Recipe> recipes = new ArrayList<Recipe>();
            recipes.add(recipeMeal.get());
            editedMeal.setRecipes(recipes);

        }

        Optional<Meal> mealFromDatabase = mealService.getMealById(editedMeal.getId());

        if(mealFromDatabase.isPresent()){
            try{
                mealFromDatabase.get().setMealName(editedMeal.getMealName());
                mealFromDatabase.get().setRecipes(editedMeal.getRecipes());
                mealService.addNewMeal(mealFromDatabase.get());
                response.sendRedirect("/user/home");
                return null;
            }catch(IOException e){
                modelAndView.addObject("recipe", new Recipe());
                modelAndView.setViewName("home.html");

            }

        }
        return modelAndView;

    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteView(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                               HttpServletResponse response,
                               @PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();
        if(userCredentials == 0){
            try{
                response.sendRedirect("/user/login");
                return null;
            }catch(IOException e){
                modelAndView.setViewName("error.html");
                return modelAndView;
            }
        }else{
            Optional<Meal> deleteMeal = mealService.getMealById(id);
            if(deleteMeal.isPresent()){
                modelAndView.addObject("deleteMeal", deleteMeal.get());
                modelAndView.addObject("recipe", new Recipe());
                modelAndView.setViewName("delete_meal.html");
            }else{
                modelAndView.setViewName("error.html");
            }

        }

        return modelAndView;
    }

    @PostMapping("/deleteMeal")
    public ModelAndView delete(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                               HttpServletResponse response,
                               @ModelAttribute Meal deleteMeal){
        ModelAndView modelAndView = new ModelAndView();
        if(userCredentials == 0){
            try{
                response.sendRedirect("/user/login");
                return null;
            }catch(IOException e){
                modelAndView.setViewName("error.html");
                return modelAndView;
            }
        }else{
            Optional<Meal> mealFromDatabase = mealService.getMealById(deleteMeal.getId());
            if(mealFromDatabase.isPresent()){
                mealService.deleteMeal(mealFromDatabase.get());
            }else{
                modelAndView.setViewName("error.html");
            }

            try{
                response.sendRedirect("/meal/viewMeal");
                return null;
            }catch(IOException e){
                modelAndView.setViewName("error.html");
            }

        }

        return modelAndView;
    }
}