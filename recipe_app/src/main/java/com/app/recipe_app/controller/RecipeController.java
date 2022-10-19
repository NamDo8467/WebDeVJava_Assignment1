package com.app.recipe_app.controller;

import com.app.recipe_app.model.Recipe;
import com.app.recipe_app.service.RecipeService;
import com.app.recipe_app.service.RecipeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RecipeController {

    @Autowired private RecipeService service;

    @GetMapping("/recipes")
    public String showRecipeList(Model model, @Param("keyword") String keyword) {
        List<Recipe> listRecipes = service.listAll(keyword);
        model.addAttribute("listRecipes",listRecipes);
        return "recipes";
    }

    @GetMapping("/recipes/new")
    public String showNewRecipe(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("pageTitle", "Add new recipe");
        return "recipe_form";
    }

    @PostMapping("recipes/save")
    public String saveRecipe(Recipe recipe, RedirectAttributes ra) {
        service.save(recipe);
        ra.addFlashAttribute("message","The recipe has been saved successfully");
        return "redirect:/recipes";
    }

    @GetMapping("recipes/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Recipe recipe = service.get(id);
            model.addAttribute("pageTitle", "Edit recipe ID "+id);
            model.addAttribute("recipe", recipe);
            return "recipe_form";
        } catch (RecipeNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());
            return "redirect:/recipes";
        }
    }

    @GetMapping("recipes/delete/{id}")
    public String deleteRecipe(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message","The recipe has been deleted");
        } catch (RecipeNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/recipes";

    }

}
