package com.app.recipe_app.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    private List<Recipe> recipes;

//    private Long recipeId;

    public User() {
    }

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    // This is for getting all recipes
    public User(Long id) {
        this.id = id;
    }

    // This is for login

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

//    public void setRecipes(List<Recipe> recipes) {
//        this.recipes = recipes;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name=" + name +
                ", recipes=" + recipes +
                '}';
    }
}
