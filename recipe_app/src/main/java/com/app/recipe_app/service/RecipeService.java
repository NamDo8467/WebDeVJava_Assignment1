package com.app.recipe_app.service;

import com.app.recipe_app.model.Recipe;
import com.app.recipe_app.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    RecipeRepository repo;

    public List<Recipe> listAll(String keyword) {
        if(keyword != null) {
            return repo.findAll(keyword);
        }
        return repo.findAll();
    }

    public void save(Recipe recipe) {
        repo.save(recipe);
    }

    public Recipe get(Long id) throws RecipeNotFoundException {
        Optional<Recipe> result = repo.findById(id);
        if(result.isPresent()) {
            return result.get();
        } else {
            throw new RecipeNotFoundException("Counld not find any recipe With ID "+id);
        }
    }

    public void delete(Long id) throws RecipeNotFoundException {
        Optional<Recipe> result = repo.findById(id);
        if(result.isPresent()) {
            repo.deleteById(id);
        } else {
            throw new RecipeNotFoundException("Counld not find any recipe With ID "+id);
        }
    }
}
