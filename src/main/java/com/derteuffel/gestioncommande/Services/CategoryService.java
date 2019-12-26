package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.Category;
import com.derteuffel.gestioncommande.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category getOne(Long categoryId) {
        return categoryRepository.getOne(categoryId);
    }

    public <S extends Category> S save(S s) {
        return categoryRepository.save(s);
    }

    public long count() {
        return categoryRepository.count();
    }

    public void deleteById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
