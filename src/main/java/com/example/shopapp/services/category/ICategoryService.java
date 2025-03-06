package com.example.shopapp.services.category;

import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.model.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(CategoryDTO category);
    void deleteCategory(long id) throws Exception;
}
