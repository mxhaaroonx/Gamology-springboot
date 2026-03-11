package com.ecommerce.Gamology.service;

import com.ecommerce.Gamology.exceptions.APIException;
import com.ecommerce.Gamology.exceptions.ResourceNotFoundException;
import com.ecommerce.Gamology.model.Category;
import com.ecommerce.Gamology.payload.CategoryDTO;
import com.ecommerce.Gamology.payload.CategoryResponse;
import com.ecommerce.Gamology.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty())
            throw new APIException("No category created");
        List<CategoryDTO> categoryDTOS= categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category=modelMapper.map(categoryDTO, Category.class);
        Category findCategory= categoryRepository.findByCategoryName(category.getCategoryName());
        if(findCategory != null)
            throw new APIException("Category exists with the name " +category.getCategoryName());

        Category savedCategory = categoryRepository.save(category);
        CategoryDTO savedCategoryDTO= modelMapper.map(savedCategory, CategoryDTO.class);
        return savedCategoryDTO;
    }

    @Override

    public CategoryDTO deleteCategory(Long categoryId) {
        Category category= categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO  categoryDTO, Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        Category category= modelMapper.map(categoryDTO, Category.class);
        category.setCategoryId(categoryId);
        savedCategory= categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

}
