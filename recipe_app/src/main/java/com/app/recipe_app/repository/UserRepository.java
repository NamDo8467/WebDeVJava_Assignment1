package com.app.recipe_app.repository;

import com.app.recipe_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Query method
    Optional<User> getUserByUsernameAndPassword(String username, String password);

}
