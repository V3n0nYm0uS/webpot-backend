package com.webpot.webpotbackend.controllers;


import com.webpot.webpotbackend.exceptions.DBException;
import com.webpot.webpotbackend.exceptions.NotFoundException;
import com.webpot.webpotbackend.models.Recipe;
import com.webpot.webpotbackend.repositories.RecipeRepository;
import com.webpot.webpotbackend.services.RecipeService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@Slf4j
public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;

    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<Recipe>> getRecipes(){
        return new ResponseEntity<>(this.recipeService.getAllRecipes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Recipe> postRecipe(@RequestBody Recipe recipeSent){
        try {
            log.info("Creating recipe ...");
            return recipeSent.getId() == null ?
                    new ResponseEntity<>(this.recipeService.updateRecipe(recipeSent), HttpStatus.CREATED) :
                    new ResponseEntity<>(this.recipeService.updateRecipe(recipeSent), HttpStatus.ACCEPTED);
        } catch (DBException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id){
        if(id == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try{
            log.info("want to delete recipe " + id);
            this.recipeService.removeRecipe(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DBException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
