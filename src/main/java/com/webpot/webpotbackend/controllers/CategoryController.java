package com.webpot.webpotbackend.controllers;

import com.webpot.webpotbackend.exceptions.DBException;
import com.webpot.webpotbackend.exceptions.NotFoundException;
import com.webpot.webpotbackend.models.Category;
import com.webpot.webpotbackend.repositories.CategoryRepository;
import com.webpot.webpotbackend.services.CategoryService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<Category>> getCategories(){
        return new ResponseEntity<>(this.categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> postCategory(@RequestBody Category categorySent){
        try{
            log.info("Creating category ...");
            return categorySent.getId() == null ?
                    new ResponseEntity<>(this.categoryService.updateCategory(categorySent), HttpStatus.CREATED) :
                    new ResponseEntity<>(this.categoryService.updateCategory(categorySent), HttpStatus.ACCEPTED);
        } catch (DBException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        if(id == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try{
            log.info("want to delete category " + id);
            this.categoryService.removeCategory(id);
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
