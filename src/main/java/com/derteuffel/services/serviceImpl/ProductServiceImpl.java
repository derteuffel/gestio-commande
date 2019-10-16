package com.derteuffel.services.serviceImpl;


import com.derteuffel.entities.Product;
import com.derteuffel.repositories.ProductRepository;
import com.derteuffel.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product saveOrUpdateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long idProduct) {
        productRepository.deleteById(idProduct);
    }

    @Override
    public Product findProductByIdProduct(Long idProduct) {
        return productRepository.getOne(idProduct);
    }
}
