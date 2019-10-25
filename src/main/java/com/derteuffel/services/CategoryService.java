package com.derteuffel.services;

import com.derteuffel.entities.Category;

import java.util.List;

public interface CategoryService {

    public List<Category>findAllCategory();
    Category saveOrUpdate(Category category);
    void deleteCategory(Long idCat);
    Category findCategoryById(Long idCat);
}
