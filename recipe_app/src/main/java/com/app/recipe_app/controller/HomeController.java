package com.app.recipe_app.controller;

import com.app.recipe_app.entity.Recipe;
import com.app.recipe_app.entity.User;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
//@RequestMapping("/")
public class HomeController {
    @GetMapping("/")
    public ModelAndView home(HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }
}
