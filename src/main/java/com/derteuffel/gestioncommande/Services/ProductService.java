package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.Product;
import com.derteuffel.gestioncommande.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
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
