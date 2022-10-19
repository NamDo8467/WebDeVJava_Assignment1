package com.app.recipe_app.service;
import com.app.recipe_app.entity.User;
import com.app.recipe_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    //This method means to be deleted after the project is finished
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> login(String username, String password){

        return userRepository.getUserByUsernameAndPassword(username, password);
    }

    public void registerUser(User user){
        userRepository.save(user);
    }
}
