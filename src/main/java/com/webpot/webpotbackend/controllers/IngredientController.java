package com.webpot.webpotbackend.controllers;


import com.webpot.webpotbackend.exceptions.DBException;
import com.webpot.webpotbackend.exceptions.NotFoundException;
import com.webpot.webpotbackend.models.Ingredient;
import com.webpot.webpotbackend.repositories.IngredientRepository;
import com.webpot.webpotbackend.services.IngredientService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ingredient")
@RequiredArgsConstructor
@Slf4j
public class IngredientController {

    private final IngredientRepository ingredientRepository;
    private final IngredientService ingredientService;

    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<Ingredient>> getIngredients(){
        return new ResponseEntity<>(this.ingredientService.getAllIngredients(), HttpStatus.OK);
    }

    @PostMapping
    @RolesAllowed("user")
    public ResponseEntity<Ingredient> postIngredient(@RequestBody Ingredient ingredientSent){
        try{
            log.info("Creating ingredient ...");
            return ingredientSent.getId() == null ?
                    new ResponseEntity<>(this.ingredientService.updateIngredient(ingredientSent), HttpStatus.CREATED) :
                    new ResponseEntity<>(this.ingredientService.updateIngredient(ingredientSent), HttpStatus.ACCEPTED);
        } catch (DBException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id){
        if(id == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try{
            log.info("want to delete " + id);
            this.ingredientService.removeIngredient(id);
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
