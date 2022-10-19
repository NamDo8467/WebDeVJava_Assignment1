package com.app.recipe_app.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.app.recipe_app.entity.User;

import com.app.recipe_app.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //This method means to be deleted after the project is finished
    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    // This method means to be deleted after the project is finished
//    @GetMapping("/redirect")
//    public void redirect(HttpServletResponse resose){
//        try {
//            resose.sendRedirect("/user/all");
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//    }

    @PostMapping("/login")
    public String login(@RequestBody User user, HttpServletResponse response){
//        System.out.println(user.getUsername() + " " + user.getPassword());
        Optional<User> queryUser = userService.login(user.getUsername(), user.getPassword());
        if(queryUser.isPresent()){
            System.out.println(queryUser.get().getId());
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
                response.sendRedirect("/recipe/all");
                return null;

            }catch(Exception e){
                System.out.println(e.getMessage());
            }

        }
        return "Wrong input";

    }

    @PostMapping("/addUser")
    public String registerNewUser(@RequestBody User user){
//        System.out.println(user);
        if(Objects.equals(user.getName(), "")){
            return "name is required";
        }
        if(Objects.equals(user.getPassword(), "")){
            return "password is required";
        }
        if(Objects.equals(user.getUsername(), "")){
            return "username is required";
        }
        userService.registerUser(user);
        return null;
    }
}
