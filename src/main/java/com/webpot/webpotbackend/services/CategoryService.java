package com.webpot.webpotbackend.services;

import com.webpot.webpotbackend.exceptions.DBException;
import com.webpot.webpotbackend.exceptions.NotFoundException;
import com.webpot.webpotbackend.models.Category;
import com.webpot.webpotbackend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){ return this.categoryRepository.findAll();}

    public Category updateCategory(Category category) throws DBException, NotFoundException {
        Category existing = null;
        if (category.getId() != null){
            existing = this.categoryRepository.findById(category.getId()).orElse(null);
            if (existing == null) throw new NotFoundException(("Category not found"));
        }
        try {
            Category categoryCreated = this.categoryRepository.save(category);
            return categoryCreated;
        } catch (Exception e){
            throw new DBException("Could not create category");
        }
    }

    public void removeCategory(Long id) throws DBException, NotFoundException {
        Category existing = null;
        existing = this.categoryRepository.findById(id).orElse(null);
        if(existing == null) throw new NotFoundException("Ingredient not found");
        try {
            this.categoryRepository.delete(existing);
        } catch (Exception e){
            throw new DBException("Could not delete category " + id);
        }
    }
}
