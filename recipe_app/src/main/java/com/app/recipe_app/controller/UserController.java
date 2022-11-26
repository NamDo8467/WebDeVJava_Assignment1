package com.app.recipe_app.controller;
import com.app.recipe_app.entity.Recipe;
import com.app.recipe_app.entity.User;

import com.app.recipe_app.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

//@Controller
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public ModelAndView home(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                             HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        if(userCredentials == 0){
            try{
                response.sendRedirect("/user/login");
                return null;
            }catch (IOException e){
                modelAndView.setViewName("error.html");
                return modelAndView;
            }
        }
        modelAndView.setViewName("home.html");
        modelAndView.addObject("recipe", new Recipe());

        return modelAndView;
    }

    @GetMapping("/view_profile")
    public ModelAndView getProfile(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials){
        ModelAndView modelAndView = new ModelAndView();

        Optional<User> user = userService.getUserById(userCredentials);
//        List<User> users = userService.getAllUsers();
        if(user.isPresent()){
            modelAndView.setViewName("profile.html");
            modelAndView.addObject("user", user.get());
            modelAndView.addObject("recipe", new Recipe());


        }else{
            modelAndView.setViewName("error.html");
        }

        return modelAndView;
    }
    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
//      modelAndView.addObject("user", new User("namdo", "huynh"));
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute User user, HttpServletResponse response){
        Optional<User> queryUser = userService.login(user.getUsername(), user.getPassword());
        ModelAndView modelAndView = new ModelAndView();
        if(queryUser.isPresent()){
//            System.out.println(queryUser.get().getId());
            // create a cookie
            String cookieValue = String.valueOf(queryUser.get().getId());
            Cookie cookie = new Cookie("userCredentials", cookieValue);

            // expires in 1 hour
            cookie.setMaxAge(60 * 60);

            // optional properties
//          cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            // add cookie to response
            response.addCookie(cookie);
            try {
                response.sendRedirect("/user/home");
            }catch(IOException e){
                modelAndView.setViewName("error.html");
            }
        }else{
            modelAndView.setViewName("login.html");
            modelAndView.addObject("error", "Username or password is incorrect");
            return modelAndView;
        }
        return null;
    }

    @GetMapping("/addUser")
    public ModelAndView registerNewUser(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration.html");
        modelAndView.addObject("user", new User());

        return modelAndView;

    }
    @PostMapping("/addUser")
    public ModelAndView registerNewUser(@ModelAttribute User user, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        try {
            userService.registerUser(user);

            response.sendRedirect("/user/login");

        } catch(DataIntegrityViolationException e){
            //modelAndView.addObject("error", "Username has already been taken");
            modelAndView.addObject("error", "Username has already been taken");
            modelAndView.setViewName("registration.html");
            return modelAndView;
        } catch (IOException e) {

//            response.sendRedirect("/user/error");
            modelAndView.setViewName("error.html");
            return modelAndView;
        }
        return null;
    }

    @PostMapping("/update")
    public ModelAndView editProfile(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                            @ModelAttribute User user,
                            HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> userFromDatabase = userService.getUserById(userCredentials);
//        System.out.println(user.getName());
        try {

            if(userFromDatabase.isPresent()){
                userFromDatabase.get().setName(user.getName());
                userFromDatabase.get().setUsername(user.getUsername());
                userService.registerUser(userFromDatabase.get());
            }

            response.sendRedirect("/user/home");

        } catch (IOException e) {

//            response.sendRedirect("/user/error");
            modelAndView.setViewName("error.html");
            return modelAndView;
        }
        return null;
    }



    @PostMapping("/logout")
    public void logout(@CookieValue(name="userCredentials", defaultValue = "0") Long userCredentials,
                       HttpServletResponse response){
        Cookie cookie = new Cookie("userCredentials", "0");
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        try{
            response.sendRedirect("/user/login");
        }catch(IOException e){
//            return null;
        }
    }
}
