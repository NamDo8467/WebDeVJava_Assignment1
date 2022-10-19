package com.app.recipe_app.service;

public class RecipeNotFoundException extends Throwable {
    public RecipeNotFoundException(String message) {
        super(message);
    }
}
