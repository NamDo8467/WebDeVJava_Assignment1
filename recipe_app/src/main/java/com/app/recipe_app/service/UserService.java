package com.app.recipe_app.service;
import com.app.recipe_app.entity.User;
import com.app.recipe_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    //This method means to be deleted after the project is finished
    public Optional<User> getUserById(Long userId){
        return userRepository.findById(userId);
    }

    public Optional<User> login(String username, String password){

        return userRepository.getUserByUsernameAndPassword(username, password);
    }

    public void registerUser(User user){
        userRepository.save(user);
    }

    public Optional<User> getUserByUsernameAndName(User user){
        return userRepository.getUserByUsernameAndName(user.getUsername(), user.getName());
    }
}
