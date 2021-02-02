package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.Product;
import com.derteuffel.gestioncommande.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC,"productId"));
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getOne(Long productId) {
        return productRepository.getOne(productId);
    }

    public <S extends Product> S save(S s) {
        return productRepository.save(s);
    }

    public long count() {
        return productRepository.count();
    }

    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }
}