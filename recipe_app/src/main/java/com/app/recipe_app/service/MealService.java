package com.app.recipe_app.service;

import com.app.recipe_app.entity.Meal;
import com.app.recipe_app.entity.Recipe;
import com.app.recipe_app.entity.User;
import com.app.recipe_app.repository.MealRepository;
import com.app.recipe_app.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MealService {
    private final MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }


    public List<Meal> getAllMeals(Long userId){
        return mealRepository.getAllByUserId(userId);
    }

//    public Optional<Meal> getMealById(Long mealId){
//        return mealRepository.getMealById(mealId);
//    }
//    public Optional<Meal> getMealByNameAndDateAdded(String mealName, Date dateAdded){
//        return mealRepository.getMealByMealNameAndDateAdded(mealName, dateAdded);
//    }

    public void addNewMeal(Meal meal) {

        mealRepository.save(meal);
    }

}