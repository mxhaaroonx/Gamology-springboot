package com.ecommerce.Gamology.service;

import com.ecommerce.Gamology.model.Category;
import com.ecommerce.Gamology.payload.CategoryDTO;
import com.ecommerce.Gamology.payload.CategoryResponse;
import com.ecommerce.Gamology.repository.CategoryRepository;

import java.util.List;


public interface CategoryService {
    CategoryResponse getAllCategories();
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
