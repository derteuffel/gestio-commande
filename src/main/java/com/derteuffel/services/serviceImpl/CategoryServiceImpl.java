package com.derteuffel.services.serviceImpl;

import com.derteuffel.entities.Category;
import com.derteuffel.repositories.CategoryRepository;
import com.derteuffel.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category saveOrUpdate(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long idCat) {
        categoryRepository.deleteById(idCat);
    }

    @Override
    public Category findCategoryById(Long idCat) {
        return categoryRepository.getOne(idCat);
    }
}
