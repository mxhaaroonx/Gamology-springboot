package com.ecommerce.Gamology.service;

import com.ecommerce.Gamology.model.Category;
import com.ecommerce.Gamology.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);

    }

    @Override

    public String deleteCategory(Long categoryId) {
        Category category= categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        categoryRepository.delete(category);
        return "Category with categoryId: " + categoryId + " deleted successfully !!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional <Category> optionalSavedCategories=categoryRepository.findById(categoryId);
        Category savedCategory= optionalSavedCategories.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        category.setCategoryId(categoryId);
        savedCategory= categoryRepository.save(category);
        return savedCategory;
    }

}
