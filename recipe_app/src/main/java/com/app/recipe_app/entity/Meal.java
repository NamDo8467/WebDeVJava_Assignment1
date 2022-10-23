package com.app.recipe_app.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="mealName")
    private String mealName;
    @Column(nullable = false, name = "date_added")
    private Date dateAdded;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "meals_recipes",
            joinColumns = { @JoinColumn(name = "meal_id") },
            inverseJoinColumns = { @JoinColumn(name = "recipe_id") })
    private List<Recipe> recipes;


    public Meal() {
    }

    public Meal(Date dateAdded, User user) {
        this.dateAdded = dateAdded;
        this.user = user;
    }



    public Meal(User user, List<Recipe> recipes) {
        this.user = user;
        this.recipes = recipes;

    }

    public Meal(Date dateAdded, User user, List<Recipe> recipes) {
        this.dateAdded = dateAdded;
        this.user = user;
        this.recipes = recipes;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;



    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }


    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateAdded=" + dateAdded +
                ", mealName='" + mealName + '\'' +
                ", user=" + user +
                ", recipes=" + recipes +
                '}';
    }
}