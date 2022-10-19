package com.app.recipe_app.repository;

import com.app.recipe_app.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    @Query("SELECT r FROM Recipe r WHERE r.title LIKE %?1%")
    public List<Recipe> findAll(String keyword);
}
