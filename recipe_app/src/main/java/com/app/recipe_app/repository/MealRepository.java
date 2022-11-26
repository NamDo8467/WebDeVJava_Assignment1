package com.app.recipe_app.repository;

import com.app.recipe_app.entity.Meal;
import com.app.recipe_app.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {
    public List<Meal> getAllByUserId(Long userId);


//    public Optional<Meal> getMealByMealNameAndDateAdded(String mealName, Date dateAded);
//
//    public Optional<Meal>getMealById(Long mealId);
}