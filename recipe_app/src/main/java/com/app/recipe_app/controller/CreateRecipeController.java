package com.app.recipe_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class CreateRecipeController {
    @GetMapping("/create_recipe")
    public String testing(){
        return "create_recipe";
    }
}
