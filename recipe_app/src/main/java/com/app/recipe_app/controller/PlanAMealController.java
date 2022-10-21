package com.app.recipe_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class PlanAMealController {
    @GetMapping("/plan_a_meal")
    public String testing(){
        return "plan_a_meal";
    }
}