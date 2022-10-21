package com.app.recipe_app.controller;

import com.app.recipe_app.entity.Meal;
import com.app.recipe_app.entity.Recipe;
import com.app.recipe_app.service.MealService;
import com.app.recipe_app.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/meal")
public class MealController {
    private final MealService mealService;

    @Autowired
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }


    @GetMapping("/all")
    public List<Meal> getAllMeals(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials)
    {
        if(userCredentials == 0L){
            System.out.println("No access");
            return null;
        }
        System.out.println(userCredentials);
        return this.mealService.getAllMeals(userCredentials);
    }

    @PostMapping("/addMeal")
    public void addNewMeal(@RequestBody Meal meal,
                           @CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials){
        if(userCredentials == 0L){
            System.out.println("No access");
//            return null;
            return;
        }


        mealService.addNewMeal(meal);
    }

}