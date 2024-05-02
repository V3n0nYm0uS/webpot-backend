package com.webpot.webpotbackend.services;


import com.webpot.webpotbackend.exceptions.DBException;
import com.webpot.webpotbackend.exceptions.NotFoundException;
import com.webpot.webpotbackend.models.Recipe;
import com.webpot.webpotbackend.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes(){ return this.recipeRepository.findAll();}

    public Recipe updateRecipe(Recipe recipe) throws DBException, NotFoundException {
        Recipe existing = null;
        if (recipe.getId() != null){
            existing = this.recipeRepository.findById(recipe.getId()).orElse(null);
            if (existing == null) throw new NotFoundException("Recipe not found");
        }
        try {
            Recipe recipeCreated = this.recipeRepository.save(recipe);
            return recipeCreated;
        } catch (Exception e){
            throw new DBException("Could not create Recipe");
        }
    }

    public void removeRecipe(Long id) throws DBException, NotFoundException {
        Recipe existing = null;
        existing = this.recipeRepository.findById(id).orElse(null);
        if(existing == null) throw new NotFoundException("Recipe not found");
        try {
            this.recipeRepository.delete(existing);
        } catch (Exception e){
            throw new DBException("Could not delete recipe " + id);
        }
    }
}
