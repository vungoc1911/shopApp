package com.example.shopapp.services.category;

import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.model.Category;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
}
