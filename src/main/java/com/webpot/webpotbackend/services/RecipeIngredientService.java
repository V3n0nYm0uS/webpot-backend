package com.webpot.webpotbackend.services;


import com.webpot.webpotbackend.repositories.RecipeIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository){
        this.recipeIngredientRepository = recipeIngredientRepository;
    }
}
