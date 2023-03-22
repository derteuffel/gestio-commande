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
        try {
            return productRepository.findAll(Sort.by(Sort.Direction.DESC,"productId"));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public List<Product> findAllByCategory(String category) {
        try {
            return productRepository.findAllByCategory(category);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }


    public Product getOne(Long productId) {
        try {
            return productRepository.getOne(productId);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public <S extends Product> S save(S s) {
        try {
            s.setProductCode("#"+Math.random()*10000000);
            return productRepository.save(s);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public long count() {
        return productRepository.count();
    }

    public void deleteById(Long productId) {
        try {
            productRepository.deleteById(productId);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
