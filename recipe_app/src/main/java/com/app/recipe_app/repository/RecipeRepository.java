package com.app.recipe_app.repository;

import com.app.recipe_app.entity.Recipe;
import com.app.recipe_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> getAllByUserId(Long userId);
    List<Recipe> getAllByTitle(String title);
}
