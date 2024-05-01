package com.webpot.webpotbackend.services;


import com.webpot.webpotbackend.exceptions.DBException;
import com.webpot.webpotbackend.exceptions.NotFoundException;
import com.webpot.webpotbackend.models.Ingredient;
import com.webpot.webpotbackend.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public List<Ingredient> getAllIngredients() { return this.ingredientRepository.findAll();}

    public Ingredient updateIngredient(Ingredient ingredient) throws DBException, NotFoundException {
        Ingredient existing = null;
        if (ingredient.getId() != null){
            existing = this.ingredientRepository.findById(ingredient.getId()).orElse(null);
            if (existing == null) throw new NotFoundException(("Ingredient not found"));
        }
        try{
            Ingredient ingredientCreated = this.ingredientRepository.save(ingredient);
            return ingredientCreated;
        } catch (Exception e){
            throw new DBException("Could not create ingredient");
        }
    }

    public void removeIngredient(Long id) throws DBException, NotFoundException {
        Ingredient existing = null;
        existing = this.ingredientRepository.findById(id).orElse(null);
        if(existing == null) throw new NotFoundException("Ingredient not found");
        try {
            this.ingredientRepository.delete(existing);
        } catch (Exception e){
            throw new DBException("Could not delete ingredient " + id);
        }
    }
}
